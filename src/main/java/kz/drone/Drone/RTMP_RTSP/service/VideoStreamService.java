package kz.drone.Drone.RTMP_RTSP.service;

import kz.drone.Drone.RTMP_RTSP.handler.VideoStreamHandler;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
public class VideoStreamService {

    private final VideoStreamHandler videoStreamHandler;
    private Process currentProcess;

    public VideoStreamService(VideoStreamHandler videoStreamHandler) {
        this.videoStreamHandler = videoStreamHandler;
    }

    public void startStreamProcessing(String rtmpUrl) {
        stopCurrentStream();

        new Thread(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "ffmpeg",
                        "-i", rtmpUrl,
                        "-vf", "fps=24",
                        "-f", "mjpeg",
                        "-q:v", "2",
                        "-"
                );

                currentProcess = processBuilder.start();
                InputStream in = currentProcess.getInputStream();
                InputStream err = currentProcess.getErrorStream();

                // Read error stream
                new Thread(() -> {
                    try {
                        ByteArrayOutputStream errorBuffer = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = err.read(buffer)) != -1) {
                            errorBuffer.write(buffer, 0, bytesRead);
                        }
                        System.out.println("FFmpeg error output:\n" + errorBuffer.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Read video stream
                ByteArrayOutputStream frameBuffer = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    frameBuffer.write(buffer, 0, bytesRead);

                    // Split MJPEG stream into individual frames
                    byte[] frameData = frameBuffer.toByteArray();
                    int start = indexOf(frameData, new byte[]{(byte)0xFF, (byte)0xD8});
                    int end = indexOf(frameData, new byte[]{(byte)0xFF, (byte)0xD9});

                    if (start != -1 && end != -1) {
                        byte[] frame = Arrays.copyOfRange(frameData, start, end + 2);
                        videoStreamHandler.broadcast(frame);
                        frameBuffer.reset();
                        frameBuffer.write(Arrays.copyOfRange(frameData, end + 2, frameData.length));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void stopCurrentStream() {
        if (currentProcess != null && currentProcess.isAlive()) {
            currentProcess.destroy();
            try {
                currentProcess.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private int indexOf(byte[] array, byte[] target) {
        outer:
        for (int i = 0; i < array.length - target.length + 1; i++) {
            for (int j = 0; j < target.length; j++) {
                if (array[i + j] != target[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }
}
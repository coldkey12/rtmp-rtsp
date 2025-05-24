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

    public VideoStreamService(VideoStreamHandler videoStreamHandler) {
        this.videoStreamHandler = videoStreamHandler;
    }

    public void startStreamProcessing(String rtmpUrl) {
        new Thread(() -> {
            try {
                Process process = new ProcessBuilder(
                        "ffmpeg",
                        "-i", rtmpUrl,
                        "-vf", "fps=15",
                        "-f", "mjpeg",          // Changed from image2pipe
                        "-q:v", "2",
                        "-"
                ).start();// Combine stdout/stderr

                InputStream in = process.getInputStream();
                ByteArrayOutputStream errorBuffer = new ByteArrayOutputStream();

                // Capture FFmpeg output in a separate thread
                new Thread(() -> {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    try {
                        while ((bytesRead = in.read(buffer)) != -1) {
                            errorBuffer.write(buffer, 0, bytesRead);
                            byte[] frame = Arrays.copyOf(buffer, bytesRead);
                            videoStreamHandler.broadcast(frame);
                        }
                    } catch (IOException e) {
                        System.err.println("FFmpeg read error: " + e.getMessage());
                    }
                }).start();

                // Wait for process exit and log errors
                int exitCode = process.waitFor();
                System.out.println("FFmpeg exited with code: " + exitCode);
                System.out.println("FFmpeg output:\n" + errorBuffer.toString());

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
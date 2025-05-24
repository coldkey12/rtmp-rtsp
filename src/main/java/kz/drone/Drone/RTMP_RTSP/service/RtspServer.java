//package kz.drone.Drone.RTMP_RTSP.service;
//
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import javax.annotation.PostConstruct;
//
//@Component
//public class RtspServer {
//
//    @Value("${rtsp.address}")
//    private String rtspAddress;
//
//    @Value("${paths.drone-stream.source}")
//    private String rtmpSource;
//
//    @PostConstruct
//    public void start() throws Exception {
//        new Thread(() -> {
//            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtmpSource);
//                 FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(rtspAddress, 0)) {
//
//                // Configure grabber
//                grabber.setOption("rtsp_transport", "tcp");
//                grabber.start();
//
//                // Configure recorder
//                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//                recorder.setFormat("rtsp");
//                recorder.start(grabber.getFormatContext());
//
//                // Transmux frames
//                while (true) {
//                    recorder.recordPacket(grabber.grabPacket());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//}

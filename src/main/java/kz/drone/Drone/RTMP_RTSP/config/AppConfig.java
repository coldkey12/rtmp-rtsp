package kz.drone.Drone.RTMP_RTSP.config;

import kz.drone.Drone.RTMP_RTSP.handler.VideoStreamHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public VideoStreamHandler videoStreamHandler() {
        return new VideoStreamHandler();
    }
}
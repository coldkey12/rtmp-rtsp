package kz.drone.Drone.RTMP_RTSP.config;

import kz.drone.Drone.RTMP_RTSP.handler.GpsWebSocketHandler;
import kz.drone.Drone.RTMP_RTSP.handler.VideoStreamHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final VideoStreamHandler videoStreamHandler;
    private final GpsWebSocketHandler gpsWebSocketHandler;

    public WebSocketConfig(VideoStreamHandler videoStreamHandler,
                           GpsWebSocketHandler gpsWebSocketHandler) {
        this.videoStreamHandler = videoStreamHandler;
        this.gpsWebSocketHandler = gpsWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(videoStreamHandler, "/stream")
                .setAllowedOrigins("*");
        registry.addHandler(gpsWebSocketHandler, "/gps")
                .setAllowedOrigins("*");
    }
}
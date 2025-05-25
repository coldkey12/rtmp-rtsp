package kz.drone.Drone.RTMP_RTSP.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.drone.Drone.RTMP_RTSP.model.GpsData;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GpsWebSocketHandler extends TextWebSocketHandler {
//    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private volatile boolean initialized = false;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
    }

    public int getSessionCount() {
        return sessions.size();
    }

    public void broadcast(GpsData gpsData) {
        try {
            System.out.println("Broadcasting GPS data: " + gpsData);
            String json = objectMapper.writeValueAsString(gpsData);
            synchronized (sessions) {
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(json));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package kz.drone.Drone.RTMP_RTSP.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class VideoStreamHandler extends BinaryWebSocketHandler { // Use Binary handler
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("New WebSocket connection: " + session.getId());
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // Handle binary messages if needed
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("WebSocket closed: " + session.getId());
    }

    public void broadcast(byte[] frame) {
        sessions.removeIf(s -> !s.isOpen());
        sessions.forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new BinaryMessage(frame));
                }
            } catch (IOException e) {
                System.err.println("Error sending message: " + e.getMessage());
            }
        });
    }
}
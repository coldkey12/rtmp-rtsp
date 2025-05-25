package kz.drone.Drone.RTMP_RTSP.service;

import kz.drone.Drone.RTMP_RTSP.handler.GpsWebSocketHandler;
import kz.drone.Drone.RTMP_RTSP.model.GpsData;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GpsService {
    private final GpsWebSocketHandler gpsWebSocketHandler;

    public void broadcastGpsData(GpsData gpsData) {
        if(gpsData == null) {
            System.out.println("Received null GPS data");
            return;
        }

        if(!isValid(gpsData)) {
            System.out.println("Invalid GPS data" +
                    " - Latitude: " + gpsData.getLatitude() +
                    ", Longitude: " + gpsData.getLongitude() +
                    ", Timestamp: " + gpsData.getTimestamp());
            return;
        }

        if(gpsWebSocketHandler.getSessionCount() == 0) {
            System.out.println("No active WebSocket sessions to broadcast GPS data");
            return;
        }

        gpsWebSocketHandler.broadcast(gpsData);
    }

    private boolean isValid(GpsData data) {
        return isValidCoordinate(data.getLatitude(), -90, 90) &&
                isValidCoordinate(data.getLongitude(), -180, 180) &&
                data.getTimestamp() > 0;
    }

    private boolean isValidCoordinate(double value, double min, double max) {
        return value >= min && value <= max;
    }
}
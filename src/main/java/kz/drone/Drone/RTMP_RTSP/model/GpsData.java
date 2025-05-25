package kz.drone.Drone.RTMP_RTSP.model;

import lombok.Data;

@Data
public class GpsData {
    private double latitude;
    private double longitude;
    private long timestamp;
}

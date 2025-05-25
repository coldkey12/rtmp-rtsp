package kz.drone.Drone.RTMP_RTSP.controller;

import kz.drone.Drone.RTMP_RTSP.dto.StreamRequest;
import kz.drone.Drone.RTMP_RTSP.model.GpsData;
import kz.drone.Drone.RTMP_RTSP.service.GpsService;
import kz.drone.Drone.RTMP_RTSP.service.VideoStreamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StreamController {

    private final VideoStreamService videoStreamService;
    private final GpsService gpsService;

    public StreamController(VideoStreamService videoStreamService,
                            GpsService gpsService) {
        this.videoStreamService = videoStreamService;
        this.gpsService = gpsService;
    }

    @PostMapping("/streams/start")
    public ResponseEntity<String> startStream(@RequestBody StreamRequest request) {
        if (!request.rtmpUrl().startsWith("rtmp://")) {
            return ResponseEntity.badRequest().body("Invalid RTMP URL");
        }

        videoStreamService.startStreamProcessing(request.rtmpUrl());
        return ResponseEntity.ok("Stream processing started");
    }

    @PostMapping("/gps")
    public ResponseEntity<String> receiveGpsData(@RequestBody GpsData gpsData) {
        if (!isValidGpsData(gpsData)) {
            return ResponseEntity.badRequest().body("Invalid GPS data");
        }

        gpsService.broadcastGpsData(gpsData);
        return ResponseEntity.ok("GPS data received");
    }

    private boolean isValidGpsData(GpsData gpsData) {
        return gpsData.getLatitude() >= -90 && gpsData.getLatitude() <= 90 &&
                gpsData.getLongitude() >= -180 && gpsData.getLongitude() <= 180;
    }
}
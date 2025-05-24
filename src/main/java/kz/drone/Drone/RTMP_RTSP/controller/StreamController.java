package kz.drone.Drone.RTMP_RTSP.controller;

import kz.drone.Drone.RTMP_RTSP.dto.StreamRequest;
import kz.drone.Drone.RTMP_RTSP.service.VideoStreamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/streams")
public class StreamController {

    private final VideoStreamService videoStreamService;

    public StreamController(VideoStreamService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startStream(@RequestBody StreamRequest request) {
        if (!request.rtmpUrl().startsWith("rtmp://")) {
            return ResponseEntity.badRequest().body("Invalid RTMP URL");
        }

        videoStreamService.startStreamProcessing(request.rtmpUrl());
        return ResponseEntity.ok("Stream processing started");
    }
}
package kz.drone.Drone.RTMP_RTSP.controller;

import kz.drone.Drone.RTMP_RTSP.model.Drone;
import kz.drone.Drone.RTMP_RTSP.model.User;
import kz.drone.Drone.RTMP_RTSP.service.DroneService;
import kz.drone.Drone.RTMP_RTSP.service.RtmpServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/drones")
public class DroneController {
    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Drone> registerDrone(@RequestBody Drone drone, @PathVariable Long userId) {
        return ResponseEntity.ok(droneService.registerDrone(drone, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Drone>> getUserDrones(@PathVariable Long userId) {
        return ResponseEntity.ok(droneService.getUserDrones(userId));
    }

    @PutMapping("/{droneId}")
    public ResponseEntity<Drone> updateDrone(@PathVariable Long droneId, @RequestBody Drone drone) {
        return ResponseEntity.ok(droneService.updateDrone(droneId, drone));
    }

    // In DroneController or a new controller
    @Autowired
    private RtmpServerService rtmpServerService;

    @PostMapping("/start-rtmp")
    public ResponseEntity<String> startRtmp(@RequestBody String configPath) {
        try {
            rtmpServerService.startRtmpServer(configPath);
            return ResponseEntity.ok("RTMP server started");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to start RTMP server: " + e.getMessage());
        }
    }
}
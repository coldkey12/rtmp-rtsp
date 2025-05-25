package kz.drone.Drone.RTMP_RTSP.repository;

import kz.drone.Drone.RTMP_RTSP.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByUserId(Long userId);
}
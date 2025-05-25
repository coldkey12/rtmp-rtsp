package kz.drone.Drone.RTMP_RTSP.service;

import kz.drone.Drone.RTMP_RTSP.model.Drone;
import kz.drone.Drone.RTMP_RTSP.repository.DroneRepository;
import kz.drone.Drone.RTMP_RTSP.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneService {
    private final DroneRepository droneRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public DroneService(DroneRepository droneRepository,
                        UserService userService, UserRepository userRepository) {
        this.droneRepository = droneRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Drone registerDrone(Drone drone, Long user) {
        drone.setUser(userRepository.findById(user).get());
        return droneRepository.save(drone);
    }

    public List<Drone> getUserDrones(Long userId) {
        return droneRepository.findByUserId(userId);
    }

    public Drone updateDrone(Long droneId, Drone updatedDrone) {
        return droneRepository.findById(droneId)
                .map(drone -> {
                    drone.setRtmpLink(updatedDrone.getRtmpLink());
                    drone.setGpsData(updatedDrone.getGpsData());
                    return droneRepository.save(drone);
                })
                .orElseThrow(() -> new RuntimeException("Drone not found"));
    }
}
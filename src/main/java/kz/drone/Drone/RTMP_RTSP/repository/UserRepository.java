package kz.drone.Drone.RTMP_RTSP.repository;

import kz.drone.Drone.RTMP_RTSP.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
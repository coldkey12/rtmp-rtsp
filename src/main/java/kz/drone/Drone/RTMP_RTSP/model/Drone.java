package kz.drone.Drone.RTMP_RTSP.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rtmpLink;
    private String gpsData;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
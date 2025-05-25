package kz.drone.Drone.RTMP_RTSP.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RtmpServerService {
    private Process rtmpProcess;

    public void startRtmpServer(String rtmpConfigPath) throws IOException {
        if (rtmpProcess == null || !rtmpProcess.isAlive()) {
            ProcessBuilder pb = new ProcessBuilder("nginx", "-c", rtmpConfigPath);
            rtmpProcess = pb.start();
        }
    }

    public void stopRtmpServer() {
        if (rtmpProcess != null && rtmpProcess.isAlive()) {
            rtmpProcess.destroy();
        }
    }
}
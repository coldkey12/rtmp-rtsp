//package kz.drone.Drone.RTMP_RTSP.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//
//@Controller
//@RequiredArgsConstructor
//public class DashboardController {
//
//    @GetMapping("/")
//    public String getDashboard(Model model, HttpServletRequest request) {
//        // Get server address dynamically
//        String serverAddress = request.getServerName() + ":" + request.getServerPort();
//
//        // Add WebSocket URLs
//        model.addAttribute("websocketUrl", "ws://" + serverAddress + "/stream");
//        model.addAttribute("gpsWebsocketUrl", "ws://" + serverAddress + "/gps");
//
//        return "index"; // Refers to src/main/resources/templates/index.html
//    }
//
//    @GetMapping("/health")
//    public String healthCheck() {
//        return "OK"; // Simple health check endpoint for Cloud Run
//    }
//}
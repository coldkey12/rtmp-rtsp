FROM openjdk:21
EXPOSE 8080
ADD target/Drone-RTMP-RTSP-0.0.1-SNAPSHOT.jar rtsp-rtmp.jar
ENTRYPOINT ["java", "-jar", "rtmp-rtsp.jar"]
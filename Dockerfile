FROM eclipse-temurin:21-jdk

# Install FFmpeg and essential libraries
RUN apt-get update && \
    apt-get install -y ffmpeg && \
    rm -rf /var/lib/apt/lists/*

# Expose necessary ports
EXPOSE 8080 1935 8554

# Copy JAR file (fix name mismatch)
ADD target/Drone-RTMP-RTSP-0.0.1-SNAPSHOT.jar rtsp-rtmp.jar

# Configure entrypoint
ENTRYPOINT ["java", "-jar", "rtsp-rtmp.jar"]
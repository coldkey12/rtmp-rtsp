FROM openjdk:21
EXPOSE 8080
ADD target/jas.ai-0.0.1-SNAPSHOT.jar jas-ai.jar
ENTRYPOINT ["java", "-jar", "jas-ai.jar"]
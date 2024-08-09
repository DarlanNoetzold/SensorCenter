FROM openjdk:22-jdk
WORKDIR /app
COPY target/SensorCenter-0.0.1-SNAPSHOT.jar /app/sensorcenter.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/sensorcenter.jar"]

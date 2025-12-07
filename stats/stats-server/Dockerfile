FROM amazoncorretto:22.0.2

WORKDIR /app

COPY target/stats-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
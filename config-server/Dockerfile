FROM eclipse-temurin:21-jre

WORKDIR /app

# Install curl so we can use an HTTP healthcheck in docker-compose
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY target/config-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
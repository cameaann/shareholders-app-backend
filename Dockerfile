# Build application
FROM maven:3.9.9-sapmachine-21 AS builder

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Run application
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/shareholders-app-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

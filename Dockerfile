# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Argument for the JAR file location
ARG JAR_FILE=target/*.jar

# Copy the JAR file from the build directory into the container
COPY ${JAR_FILE} /spring-boot-docker.jar

# Expose the application port (change if your Spring Boot app runs on a different port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/spring-boot-docker.jar"]


# Step one is the build stage.
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy the project files to the container.
COPY pom.xml .
COPY src ./src

# Build the application.
RUN mvn clean package -DskipTests

# Creates the runtime image for step two.
FROM openjdk:19-jdk-alpine
WORKDIR /app

# Useful labels.
LABEL maintainer="Christopher PIHET <pihet.christopher@gmail.com>"
LABEL description="Micro Service Based on Spring Boot (Java)."

# Copy the jar file from the build stage.
COPY --from=build /app/target/*.jar app.jar

# Entry point and parameters.
ENTRYPOINT ["java", "-jar", "app.jar"]
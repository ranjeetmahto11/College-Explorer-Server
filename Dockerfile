# Stage 1: Build your app using Maven and Java 17 (as per your pom.xml java.version)
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

# Copy only the necessary files to leverage Docker layer caching
COPY pom.xml .
COPY src ./src

# Build the project and skip tests for faster build (optional)
RUN mvn clean package -DskipTests

# Stage 2: Run the app with a lightweight Java 17 runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar from the build stage (matches your artifactId and version)
COPY --from=build /app/target/college-explorer-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]

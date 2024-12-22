# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the local target directory to the container's /app directory
COPY target/todo-app-1.0.0.jar todo-app-1.0.0.jar

# Run the JAR file

ENTRYPOINT ["java", "-jar", "todo-app-1.0.0.jar"]

# Expose the port the app will run on
EXPOSE 8080
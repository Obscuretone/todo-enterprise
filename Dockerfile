FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/todo-app-1.0.0.jar todo-app-1.0.0.jar

ENTRYPOINT ["java", "-jar", "todo-app-1.0.0.jar"]

EXPOSE 8080

FROM openjdk:17-jdk-slim
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY src/main/resources/static /app/resources/static
ENTRYPOINT ["java", "-jar", "/app.jar"]
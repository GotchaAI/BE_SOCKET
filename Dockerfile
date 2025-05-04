# 1. 베이스 이미지
FROM openjdk:17-jdk-slim

# 2. 빌드된 JAR 복사
COPY build/libs/myapp-0.0.1-SNAPSHOT.jar app.jar

# 3. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

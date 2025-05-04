# 1. 베이스 이미지: 경량 Java 환경
FROM openjdk:17-jdk-slim

# 2. 외부에서 전달받을 JAR 파일명 (GitHub Actions에서 --build-arg로 넣음)
ARG JAR_FILE=myapp-0.0.1-SNAPSHOT.jar

# 3. 전달받은 JAR 파일을 이미지 내부로 복사
COPY ${JAR_FILE} app.jar

# 4. Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 1. 베이스 이미지
FROM openjdk:17-jdk-slim
# 2. JAR 파일 복사
COPY build/libs/myapp-0.0.1-SNAPSHOT.jar app.jar
# 3. 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 1. 베이스 이미지
FROM openjdk:17-jdk-slim
# netcat 설치
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*
# 2. JAR 파일 복사
COPY build/libs/myapp-0.0.1-SNAPSHOT.jar app.jar
# 포트 노출
EXPOSE 8080
# 3. 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

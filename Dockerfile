# 1. 베이스 이미지
FROM openjdk:17-jdk-slim

# 2. build arg 선언
ARG JAR_FILE=build/libs/myapp-0.0.1-SNAPSHOT.jar

# 3. 복사
COPY ${JAR_FILE} app.jar

# 4. 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
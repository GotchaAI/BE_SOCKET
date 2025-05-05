# 1. Builder 스테이지: JAR 생성
FROM openjdk:17-jdk-slim AS builder

# gradlew 스크립트와 설정 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# gradlew 실행 권한 및 필요 패키지 설치
RUN chmod +x ./gradlew \
 && apt-get update \
 && apt-get install -y findutils netcat \
 && rm -rf /var/lib/apt/lists/*

# 애플리케이션 빌드 (테스트 제외)
RUN ./gradlew build -x test

# 2. Final 스테이지: 런타임 이미지
FROM openjdk:17-jdk-slim

# netcat 설치 (필요 시)
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

# 빌더에서 만든 JAR 복사
COPY --from=builder build/libs/myapp-0.0.1-SNAPSHOT.jar /app/app.jar

# 작업 디렉토리 설정
WORKDIR /app

# 포트 노출
EXPOSE 8080

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

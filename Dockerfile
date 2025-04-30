# 1. 베이스 이미지
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리
WORKDIR /app

# 3. 빌드한 JAR 복사
COPY build/libs/payment.jar app.jar

# 4. 포트 오픈 (개인포트)
EXPOSE 19096

# 5. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
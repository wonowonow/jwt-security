FROM openjdk:17-jdk-slim
ARG FILE_DIRECTORY

# 시간대 설정
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir /app

ARG JAR_FILE=${FILE_DIRECTORY}/build/libs/*.jar
COPY ${JAR_FILE} /app/app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=deploy", "-jar", "/app/app.jar"]
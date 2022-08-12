FROM adoptopenjdk/openjdk11:latest
FROM maven:3-jdk-8-alpine

#WORKDIR /app
RUN mvn clean install
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
# Docker Build Stage
FROM maven:3-jdk-8-alpine AS build

WORKDIR /opt/app

#ARG JAR_FILE=target/*.jar
COPY ./ /opt/app
RUN mvn clean install

FROM adoptopenjdk/openjdk11:latest

COPY --from=build /opt/app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
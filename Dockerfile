FROM adoptopenjdk/openjdk11:latest
FROM maven:3-jdk-8-alpine

ENV HOST_IP localhost
ENV DATABASE_USER postgres
ENV DATABASE_PASSWORD 13111992

#WORKDIR /app
RUN mvn clean
RUN mvn install -D $HOST_IP -D $DATABASE_USER -D $DATABASE_PASSWORD

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
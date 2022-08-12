FROM maven:3.3-jdk-8 AS build

ENV HOST_IP localhost
ENV DATABASE_USER postgres
ENV DATABASE_PASSWORD 13111992

WORKDIR /app

#RUN mvn clean
#RUN mvn install -D $HOST_IP -D $DATABASE_USER -D $DATABASE_PASSWORD

FROM adoptopenjdk/openjdk11:latest
COPY --from=build /app/target/*.jar app.jar

#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","-Xmx1024M","-D HOST_IP=${HOST_IP} -D DATABASE_USER=${DATABASE_USER} -D DATABASE_PASSWORD=${DATABASE_PASSWORD}","app.jar"]
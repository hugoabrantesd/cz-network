FROM adoptopenjdk/openjdk11:latest
WORKDIR /app

RUN apt-get install maven
RUN mvn clean install

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
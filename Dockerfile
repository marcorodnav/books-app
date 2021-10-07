FROM openjdk:8-jdk-alpine
MAINTAINER marcorodnav
VOLUME /tmp
EXPOSE 9090
ARG JAR_FILE=target/books-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
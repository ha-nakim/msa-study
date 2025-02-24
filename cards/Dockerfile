FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cardApp.jar
ENTRYPOINT ["java","-jar","cardApp.jar"]
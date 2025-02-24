FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} loanApp.jar
ENTRYPOINT ["java","-jar","loanApp.jar"]
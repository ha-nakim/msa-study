#Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/*.jar

# Add the application's jar to the image
COPY ${JAR_FILE} gatewayserver.jar

# execute the application
ENTRYPOINT ["java", "-jar", "gatewayserver.jar"]
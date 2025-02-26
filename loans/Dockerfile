#Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/*.jar

# Add the application's jar to the image
COPY ${JAR_FILE} loans.jar

# execute the application
ENTRYPOINT ["java", "-jar", "loans.jar", "--spring.profiles.active=prod"]
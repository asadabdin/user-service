# Dockerfile
# Package stage
#
FROM openjdk:11-jre-slim
COPY target/user-service-homework-*.jar /opt/app/user-service-homework.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/opt/app/user-service-homework.jar"]

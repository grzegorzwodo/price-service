# Dockerfile
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY build/libs/price-service-0.0.1-SNAPSHOT.jar price-service.jar
ENTRYPOINT ["java","-jar","/price-service.jar"]
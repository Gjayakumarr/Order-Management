FROM openjdk:17-jdk-alpine
WORKDIR /docker
#ENV PORT 8989
EXPOSE 8989
COPY target/user-microservice.jar /docker/user-microservice.jar
ENTRYPOINT [ "java" , "-jar" , "user-microservice.jar" ]
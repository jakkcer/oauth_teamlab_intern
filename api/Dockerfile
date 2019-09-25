FROM openjdk:8-jdk-alpine

# recommend by spring boot
VOLUME /tmp

# create directory for application
RUN mkdir /api
WORKDIR /api

# jar target
ENV JAR_TARGET "userAuthAPI-0.0.1-SNAPSHOT.jar"

# set entrypoint to execute spring boot application
ENTRYPOINT ["sh","-c","java -jar userAuthAPI/build/libs/${JAR_TARGET} --spring.profiles.active=docker"]


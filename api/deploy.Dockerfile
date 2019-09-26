FROM openjdk:8-jdk-alpine

# recommend by spring boot
VOLUME /tmp

# copy spring project
COPY userAuthAPI ./

# jar target
ENV JAR_TARGET "userAuthAPI-0.0.1-SNAPSHOT.jar"

# set entrypoint to execute spring boot application
ENTRYPOINT ["sh","-c","java -jar build/libs/${JAR_TARGET} --spring.profiles.active=docker"]


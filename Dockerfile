FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
ARG JAR_FILE=target/MySpringBoot-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

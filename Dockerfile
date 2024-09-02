FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
ARG JAR_FILE=target/MySpringBoot-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app3
COPY ${JAR_FILE} app3.jar
ENTRYPOINT ["java","-jar","app3.jar"]

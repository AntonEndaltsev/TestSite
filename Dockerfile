FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
ARG JAR_FILE=target/MySpringBoot-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app2
COPY ${JAR_FILE} app2.jar
ENTRYPOINT ["java","-jar","app2.jar"]

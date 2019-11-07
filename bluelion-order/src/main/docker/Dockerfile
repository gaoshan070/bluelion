FROM openjdk:8-jdk-alpine
MAINTAINER Shan Gao <gaoshan070@gmail.com>
VOLUME /tmp
ADD bluelion-order-1.0-SNAPSHOT.jar app.jar
EXPOSE 8504
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
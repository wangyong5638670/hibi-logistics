FROM openjdk:8-jdk-alpine

VOLUME /tmp
#ADD target/hibi-springcloud-security.jar app.jar
ADD hibi-springcloud-logistics.jar app.jar
EXPOSE 8100
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
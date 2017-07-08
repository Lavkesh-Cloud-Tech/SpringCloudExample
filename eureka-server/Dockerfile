FROM openjdk:8u131-jdk
VOLUME /tmp

ADD build/libs/eureka_discovery_server.jar /app.jar
RUN touch /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

EXPOSE 8080
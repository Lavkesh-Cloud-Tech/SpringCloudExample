FROM openjdk:8u131-jdk
VOLUME /tmp

ADD build/libs/config-server.jar /app.jar
RUN touch /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080 9080
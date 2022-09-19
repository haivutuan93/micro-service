FROM openjdk:8

VOLUME /tmp

ADD target/spring-eureka-server-0.0.1-SNAPSHOT.jar app-server.jar

ENTRYPOINT exec java -jar app-server.jar
FROM openjdk:8

VOLUME /tmp

ADD target/spring-eureka-auth-0.0.1-SNAPSHOT.jar auth.jar

ENTRYPOINT exec java -jar auth.jar
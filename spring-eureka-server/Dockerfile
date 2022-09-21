FROM openjdk:8

VOLUME /nab/tmp

ADD target/spring-eureka-server-0.0.1-SNAPSHOT.jar eureka.jar

ENTRYPOINT exec java -jar eureka.jar
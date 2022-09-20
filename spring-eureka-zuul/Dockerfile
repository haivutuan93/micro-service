FROM openjdk:8

VOLUME /nab/tmp

ADD spring-eureka-zull-0.0.1-SNAPSHOT.jar zuul-server.jar

ENTRYPOINT exec java -jar zuul-server.jar
FROM openjdk:8

VOLUME /nab/tmp

ADD target/spring-eureka-zuul-0.0.1-SNAPSHOT.jar zuul.jar

ENTRYPOINT exec java -jar zuul.jar
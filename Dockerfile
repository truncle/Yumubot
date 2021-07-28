FROM openjdk:11.0.11-jre
MAINTAINER Yumubot
VOLUME /tmp
ADD target/Yumubot-0.0.1-SNAPSHOT.jar bot.jar
ENTRYPOINT ["java","-jar","/bot.jar"]
#EXPOSE 5700
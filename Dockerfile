FROM openjdk:8-jre-alpine
MAINTAINER bangwei.mo <bangwei.mo@lifesense.com>
ADD redissonx-server/target/redissonx-server-0.0.1-SNAPSHOT.jar /data/redissonx/app.jar
ADD  redissonx-server/src/main/resources/application.yml /data/redissonx/application.yml
WORKDIR /data/redissonx
EXPOSE 8080
ENTRYPOINT ["nohup","java","-jar","/data/redissonx/app.jar","--spring.config.location=file:/data/redissonx/application.yml","&"]

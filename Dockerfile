FROM harbor.lifesense.com/base/lsg-javaapp:springboot-0.0.1
MAINTAINER bangwei.mo <bangwei.mo@lifesense.com>
ADD redissonx-server/target/redissonx-server-0.0.1-SNAPSHOT.jar /data/apps/app.jar
ADD redissonx-server/src/main/resources/application.yml /data/apps/application.yml
ENV ENV=beta
WORKDIR /data/apps
EXPOSE 8080
ENTRYPOINT ["sh","/data/apps/scripts/install.sh","start"]

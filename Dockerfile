FROM harbor.lifesense.com/base/springboot:springboot-0.0.1
MAINTAINER bangwei.mo <bangwei.mo@lifesense.com>
ADD redissonx-server/env.properties /data/appenvs/env.properties
ADD redissonx-server/target/redissonx-server-0.0.1-SNAPSHOT.jar /data/apps/app.jar
ADD redissonx-server/src/main/resources/application.yml /data/apps/application.yml
WORKDIR /data/apps
EXPOSE 8080
ENTRYPOINT ["sh","/data/apps/scripts/service.sh","start"]

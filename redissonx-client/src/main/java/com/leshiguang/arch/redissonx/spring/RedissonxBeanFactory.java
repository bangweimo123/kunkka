package com.leshiguang.arch.redissonx.spring;

import org.redission.config.ApolloRedissonxConfigLoader;
import org.redission.config.RedissonxConfigLoader;
import org.redission.config.ZookeeperRedissonxConfigLoader;
import org.redisson.Redissonx;
import org.redisson.RedissonxClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-13 16:26
 * @Modify
 */
public class RedissonxBeanFactory implements FactoryBean, DisposableBean, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonxBeanFactory.class);

    private String clusterName;

    private RedissonxConfigLoader configLoader;

    private RedissonxClient redissonxClient;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public RedissonxConfigLoader getConfigLoader() {
        return configLoader;
    }

    public void setConfigLoader(RedissonxConfigLoader configLoader) {
        this.configLoader = configLoader;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == configLoader) {
            configLoader = new ZookeeperRedissonxConfigLoader();
        }
        Config config = configLoader.getByCluster(clusterName);
        if (null == config) {
            LOGGER.error("can't find config from apollo for redissonx cluster:[" + clusterName + "]");
        }
        redissonxClient = Redissonx.create(clusterName, config);
    }

    public RedissonxClient getObject() throws Exception {
        return redissonxClient;
    }

    @Override
    public Class<?> getObjectType() {
        return RedissonxClient.class;
    }

    @Override
    public void destroy() throws Exception {
        redissonxClient.shutdown();
    }
}

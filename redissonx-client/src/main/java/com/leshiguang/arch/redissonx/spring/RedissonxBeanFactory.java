package com.leshiguang.arch.redissonx.spring;

import org.redisson.config.RedissonxConfigLoader;
import org.redisson.config.RedissonxConnectConfig;
import org.redisson.config.ZookeeperRedissonxConfigLoader;
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
    /**
     * 集群名
     */
    private String clusterName;
    /**
     * 连接配置
     */
    private RedissonxConnectConfig connectConfig;

    private RedissonxConfigLoader configLoader;

    private RedissonxClient redissonxClient;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public RedissonxConnectConfig getConnectConfig() {
        return connectConfig;
    }

    public void setConnectConfig(RedissonxConnectConfig connectConfig) {
        this.connectConfig = connectConfig;
    }

    public void setConfigLoader(RedissonxConfigLoader configLoader) {
        this.configLoader = configLoader;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == configLoader) {
            configLoader = new ZookeeperRedissonxConfigLoader();
        }
        Config config = configLoader.getByCluster(clusterName, connectConfig);
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

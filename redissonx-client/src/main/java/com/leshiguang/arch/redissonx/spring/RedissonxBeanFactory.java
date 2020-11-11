package com.leshiguang.arch.redissonx.spring;

import com.leshiguang.arch.redissonx.exception.ConfigException;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientFactory;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redissonx;
import org.redisson.RedissonxClient;
import org.redisson.config.*;
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
        if (StringUtils.isBlank(clusterName)) {
            throw new ConfigException("clusterName can not be empty!");
        }
        RedissonxConfig redissonConfig = configLoader.getByCluster(clusterName, connectConfig);
        if (null == redissonConfig) {
            throw new ConfigException("clusterName:[" + clusterName + "]  can't find config from " + configLoader.getName());
        }
        redissonxClient = Redissonx.create(clusterName, redissonConfig);

        ZookeeperClient zookeeperClient = ZookeeperClientFactory.getInstance(redissonConfig.getRegion());
        zookeeperClient.addConnectConfigListener(redissonConfig.getClusterName(), new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                reloadClient(clusterName);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                reloadClient(clusterName);
            }
        });
        LOGGER.info("redissonx inited cluster:[" + clusterName + "]");
    }

    public RedissonxClient getObject() throws Exception {
        return redissonxClient;
    }

    private void reloadClient(String clusterName) {
        RedissonxConfig redissonConfig = configLoader.getByCluster(clusterName, connectConfig);
        if (null == redissonConfig) {
            throw new ConfigException("clusterName:[" + clusterName + "]  can't find config from " + configLoader.getName());
        }
        RedissonxClient _reload = Redissonx.create(clusterName, redissonConfig);
        synchronized (this) {
            RedissonxClient _refer = redissonxClient;
            try {
                redissonxClient = _reload;
            } finally {
                _refer.shutdown();
            }
        }
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

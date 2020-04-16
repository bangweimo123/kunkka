package org.redisson.config;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 18:59
 * @Modify
 */
public interface RedissonxConfigLoader {
    Config getByCluster(String clusterName);

    Config getByCluster(String clusterName, RedissonxConnectConfig connectConfig);
}

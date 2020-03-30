package org.redisson.config;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 18:59
 * @Modify
 */
public interface RedissonxConfigLoader {
    public org.redisson.config.Config getByCluster(String clusterName);
}

package org.redisson.config;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 18:59
 * @Modify configloader, 通过集群组/集群名 获取相应的配置
 */
public interface RedissonxConfigLoader {
    String getName();

    Config getByCluster(String clusterName);

    Config getByCluster(String clusterName, RedissonxConnectConfig connectConfig);

    Config getByClusterAndRegion(String clusterName, String reigon);

    Config getByClusterAndRegion(String clusterName, String region, RedissonxConnectConfig connectConfig);
}

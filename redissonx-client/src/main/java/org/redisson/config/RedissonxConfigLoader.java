package org.redisson.config;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 18:59
 * @Modify configloader, 通过集群组/集群名 获取相应的配置
 */
public interface RedissonxConfigLoader {
    String getName();

    RedissonxConfig getByCluster(String clusterName, RedissonxConnectConfig connectConfig);

    RedissonxConfig getByClusterAndRegion(String clusterName, String reigon);

    RedissonxConfig getByClusterAndRegion(String clusterName, String region, RedissonxConnectConfig connectConfig);
}

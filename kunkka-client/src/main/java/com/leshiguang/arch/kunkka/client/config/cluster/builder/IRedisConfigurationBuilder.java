package com.leshiguang.arch.kunkka.client.config.cluster.builder;

import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterConnectParamsBO;
import org.redisson.config.Config;
import org.springframework.data.redis.connection.RedisConfiguration;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-13 15:38
 * @Description
 */
public interface IRedisConfigurationBuilder<C extends RedisConfiguration> {
    C build(ClusterBO clusterBO);

    Config redissonBuild(ClusterBO clusterBO);
}

package org.redisson.config;

import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 16:50
 * @Modify
 */
public interface IConfigBuilder {
    RedissonxConfig build(ClusterBO clusterBO, RedissonxConnectConfig connectConfig);
}

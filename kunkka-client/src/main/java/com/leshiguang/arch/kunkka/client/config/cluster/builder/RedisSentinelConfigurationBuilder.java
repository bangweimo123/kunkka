package com.leshiguang.arch.kunkka.client.config.cluster.builder;

import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.scaffold.common.utils.AppUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-13 16:03
 * @Description
 */
public class RedisSentinelConfigurationBuilder extends AbstractRedissionConfigBuilder implements IRedisConfigurationBuilder<RedisSentinelConfiguration> {
    @Override
    public RedisSentinelConfiguration build(ClusterBO cluster) {
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
        configuration.setMaster(cluster.getMasterNode().getConnectName());
        configuration.setDatabase(cluster.getDatabase());
        configuration.setPassword(StringUtils.isBlank(cluster.getPassword()) ? RedisPassword.none() : RedisPassword.of(cluster.getPassword()));
        if (CollectionUtils.isNotEmpty(cluster.getSlaveNodes())) {
            for (ConnectBO connectBO : cluster.getSlaveNodes()) {
                configuration.sentinel(connectBO.getHostName(), connectBO.getPort());
                configuration.setSentinelPassword(StringUtils.isBlank(cluster.getPassword()) ? RedisPassword.none() : RedisPassword.of(cluster.getPassword()));
            }
        }
        return configuration;
    }

    @Override
    public Config redissonBuild(ClusterBO cluster) {
        Config config = new Config();
        SentinelServersConfig serversConfig = config.useSentinelServers();
        serversConfig.setDatabase(cluster.getDatabase());
        serversConfig.setMasterName(cluster.getMasterNode().getConnectName());
        if (StringUtils.isNotBlank(cluster.getPassword())) {
            serversConfig.setPassword(cluster.getPassword());
        }
        if (CollectionUtils.isNotEmpty(cluster.getSlaveNodes())) {
            for (ConnectBO slaveNode : cluster.getSlaveNodes()) {
                serversConfig.addSentinelAddress(processAddress(slaveNode));
            }
        }
        if (null != cluster.getConnectParams()) {
            processClusterConnectParams(serversConfig, cluster.getConnectParams());
        }
        return config;
    }
}

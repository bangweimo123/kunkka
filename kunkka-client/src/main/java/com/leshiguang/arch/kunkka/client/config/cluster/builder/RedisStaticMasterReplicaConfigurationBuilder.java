package com.leshiguang.arch.kunkka.client.config.cluster.builder;

import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.scaffold.common.utils.AppUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.redisson.config.ReplicatedServersConfig;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-13 19:05
 * @Description
 */
public class RedisStaticMasterReplicaConfigurationBuilder extends AbstractRedissionConfigBuilder implements IRedisConfigurationBuilder<RedisStaticMasterReplicaConfiguration> {
    @Override
    public RedisStaticMasterReplicaConfiguration build(ClusterBO cluster) {
        ConnectBO connectBO = cluster.getMasterNode();
        RedisStaticMasterReplicaConfiguration configuration = new RedisStaticMasterReplicaConfiguration(connectBO.getHostName(), connectBO.getPort());
        configuration.setDatabase(cluster.getDatabase());
        if (CollectionUtils.isNotEmpty(cluster.getSlaveNodes())) {
            for (ConnectBO slaveConnectBO : cluster.getSlaveNodes()) {
                configuration.addNode(slaveConnectBO.getHostName(), slaveConnectBO.getPort());
            }
        }
        configuration.setPassword(StringUtils.isBlank(cluster.getPassword()) ? RedisPassword.none() : RedisPassword.of(cluster.getPassword()));
        return configuration;
    }

    @Override
    public Config redissonBuild(ClusterBO cluster) {
        Config config = new Config();
        ReplicatedServersConfig serversConfig = config.useReplicatedServers();
        serversConfig.setDatabase(cluster.getDatabase());
        if (StringUtils.isNotBlank(cluster.getPassword())) {
            serversConfig.setPassword(cluster.getPassword());
        }
        serversConfig.addNodeAddress(processAddress(cluster.getMasterNode()));
        if (CollectionUtils.isNotEmpty(cluster.getSlaveNodes())) {
            for (ConnectBO slaveNode : cluster.getSlaveNodes()) {
                serversConfig.addNodeAddress(processAddress(slaveNode));
            }
        }
        serversConfig.setClientName(AppUtil.appName());
        if (null != cluster.getConnectParams()) {
            processClusterConnectParams(serversConfig, cluster.getConnectParams());
        }
        return config;
    }
}

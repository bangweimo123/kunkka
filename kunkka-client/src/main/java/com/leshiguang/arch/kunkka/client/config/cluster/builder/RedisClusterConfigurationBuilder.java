package com.leshiguang.arch.kunkka.client.config.cluster.builder;

import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.scaffold.common.utils.AppUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-13 15:51
 * @Description
 */
public class RedisClusterConfigurationBuilder extends AbstractRedissionConfigBuilder implements IRedisConfigurationBuilder<RedisClusterConfiguration> {
    private RedisClusterNode.RedisClusterNodeBuilder builder = new RedisClusterNode.RedisClusterNodeBuilder();

    @Override
    public RedisClusterConfiguration build(ClusterBO cluster) {
        RedisClusterConfiguration configuration = new RedisClusterConfiguration();
        configuration.addClusterNode(
                builder.withName(cluster.getMasterNode().getConnectName())
                        .withId(cluster.getMasterNode().getConnectName())
                        .listeningAt(cluster.getMasterNode().getHostName(), cluster.getMasterNode().getPort())
                        .promotedAs(RedisNode.NodeType.MASTER)
                        .linkState(RedisClusterNode.LinkState.CONNECTED)
                        .build()
        );
        if (CollectionUtils.isNotEmpty(cluster.getSlaveNodes())) {
            for (ConnectBO connectBO : cluster.getSlaveNodes()) {
                configuration.addClusterNode(builder.withName(connectBO.getConnectName())
                        .withId(connectBO.getConnectName())
                        .listeningAt(connectBO.getHostName(), connectBO.getPort())
                        .promotedAs(RedisNode.NodeType.SLAVE)
                        .linkState(RedisClusterNode.LinkState.CONNECTED)
                        .slaveOf(cluster.getMasterNode().getConnectName())
                        .build());
            }
        }
        configuration.setPassword(StringUtils.isBlank(cluster.getPassword()) ? RedisPassword.none() : RedisPassword.of(cluster.getPassword()));
        configuration.setMaxRedirects(100);
        return configuration;
    }

    @Override
    public Config redissonBuild(ClusterBO cluster) {
        Config config = new Config();
        ClusterServersConfig serversConfig = config.useClusterServers();
        if (StringUtils.isNotBlank(cluster.getPassword())) {
            serversConfig.setPassword(cluster.getPassword());
        }
        serversConfig.addNodeAddress(processAddress(cluster.getMasterNode()));
        if (CollectionUtils.isNotEmpty(cluster.getSlaveNodes())) {
            for (ConnectBO slaveNode : cluster.getSlaveNodes()) {
                serversConfig.addNodeAddress(processAddress(slaveNode));
            }
        }
//        serversConfig.setClientName(AppUtil.appName());
        if (null != cluster.getConnectParams()) {
            processClusterConnectParams(serversConfig, cluster.getConnectParams());
        }
        return config;
    }
}

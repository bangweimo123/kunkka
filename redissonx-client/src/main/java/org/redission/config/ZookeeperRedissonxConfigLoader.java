package org.redission.config;

import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterConnectBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterInnerSingleBO;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientImpl;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.SingleServerConfig;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-24 17:06
 * @Modify
 */
public class ZookeeperRedissonxConfigLoader implements RedissonxConfigLoader {
    private ZookeeperClient zookeeperClient = new ZookeeperClientImpl();

    @Override
    public org.redisson.config.Config getByCluster(String clusterName) {
        ClusterBO clusterBO = zookeeperClient.getCluster(clusterName);
        if (null != clusterBO) {
            String clusterMode = clusterBO.getMode();
            switch (clusterMode) {
                case "single":
                    return buildSingleConfig(clusterBO);

            }
        }
        return null;
    }

    private org.redisson.config.Config buildSingleConfig(ClusterBO clusterBO) {
        org.redisson.config.Config redissonConfig = new org.redisson.config.Config();
        SingleServerConfig singleServerConfig = redissonConfig.useSingleServer();
        if (null != clusterBO.getInner()) {
            ClusterInnerSingleBO singleBO = (ClusterInnerSingleBO) clusterBO.getInner();
            String address = singleBO.getAddress();
            Boolean useHttpsMode = singleBO.getUseHttpsMode();
            singleServerConfig.setAddress((useHttpsMode ? "rediss" : "redis") + "://" + address);
            Integer database = singleBO.getDatabase();
            if (null != database) {
                singleServerConfig.setDatabase(database);
            }
            Integer connectionMinimumIdleSize = singleBO.getMinIdleSize();
            singleServerConfig.setConnectionPoolSize(connectionMinimumIdleSize);

            Integer connectionPoolSize = singleBO.getConnectPoolSize();
            if (null != connectionPoolSize) {
                singleServerConfig.setConnectionPoolSize(connectionPoolSize);
            }

            Integer subscriptionConnectionMinimumIdleSize = singleBO.getSubscriptionConnectMinimumIdleSize();
            if (null != subscriptionConnectionMinimumIdleSize) {
                singleServerConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionMinimumIdleSize);
            }


            Integer subscriptionConnectionPoolSize = singleBO.getSubscriptionConnectPoolSize();
            if (null != subscriptionConnectionPoolSize) {
                singleServerConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize);
            }
        }

        ClusterConnectBO connectBO = clusterBO.getConnect();
        if (null != connectBO) {
            Integer coonectionTimeout = connectBO.getConnectTimeout();
            if (null != coonectionTimeout) {
                singleServerConfig.setConnectTimeout(coonectionTimeout);
            }
            Integer idleConnectionTimeout = connectBO.getIdleConenctTimeout();
            if (null != idleConnectionTimeout) {
                singleServerConfig.setIdleConnectionTimeout(idleConnectionTimeout);
            }
            Integer retryAttempts = connectBO.getRetryAttempts();
            if (null != retryAttempts) {
                singleServerConfig.setRetryAttempts(retryAttempts);
            }
            Integer retryInterval = connectBO.getRetryInterval();
            if (null != retryInterval) {
                singleServerConfig.setRetryAttempts(retryInterval);
            }
        }
        String authMode = clusterBO.getAuthMode();
        if (StringUtils.isNotBlank(authMode)) {
            switch (authMode) {
                case "none":
                    break;
                case "password":
                    String password = clusterBO.getPassword();
                    if (StringUtils.isNotBlank(password)) {
                        singleServerConfig.setPassword(password);
                    }
                    break;
                case "ssh":
                    break;
            }
        }
        return redissonConfig;
    }
}

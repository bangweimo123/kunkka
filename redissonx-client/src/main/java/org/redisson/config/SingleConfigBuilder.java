package org.redisson.config;

import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterConnectBO;
import com.leshiguang.redissonx.common.entity.connect.ConnectPasswordBO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 16:52
 * @Modify
 */
public class SingleConfigBuilder implements IConfigBuilder {
    @Override
    public RedissonxConfig build(ClusterBO clusterBO, RedissonxConnectConfig connectConfig) {
        RedissonxConfig redissonConfig = new RedissonxConfig();
        SingleServerConfig singleServerConfig = redissonConfig.useSingleServer();
        List<ClusterConnectBO> connectList = clusterBO.getConnectList();
        ClusterConnectBO connect = connectList.get(0);
        Boolean useHttpsMode = connect.getConnect().getUseHttpsMode();
        String address = connect.getConnect().getAddress();
        singleServerConfig.setAddress((useHttpsMode ? "rediss" : "redis") + "://" + address);
        Integer database = connect.getDatabase();
        if (null != database) {
            singleServerConfig.setDatabase(database);
        }
        if (null != connectConfig) {
            Integer connectionMinimumIdleSize = connectConfig.getMinIdleSize();
            singleServerConfig.setConnectionPoolSize(connectionMinimumIdleSize);

            Integer connectionPoolSize = connectConfig.getConnectPoolSize();
            if (null != connectionPoolSize) {
                singleServerConfig.setConnectionPoolSize(connectionPoolSize);
            }

            Integer subscriptionConnectionMinimumIdleSize = connectConfig.getSubscriptionConnectMinimumIdleSize();
            if (null != subscriptionConnectionMinimumIdleSize) {
                singleServerConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionMinimumIdleSize);
            }
            Integer subscriptionConnectionPoolSize = connectConfig.getSubscriptionConnectPoolSize();
            if (null != subscriptionConnectionPoolSize) {
                singleServerConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize);
            }
            Integer coonectionTimeout = connectConfig.getConnectTimeout();
            if (null != coonectionTimeout) {
                singleServerConfig.setConnectTimeout(coonectionTimeout);
            }
            Integer idleConnectionTimeout = connectConfig.getIdleConenctTimeout();
            if (null != idleConnectionTimeout) {
                singleServerConfig.setIdleConnectionTimeout(idleConnectionTimeout);
            }
            Integer retryAttempts = connectConfig.getRetryAttempts();
            if (null != retryAttempts) {
                singleServerConfig.setRetryAttempts(retryAttempts);
            }
            Integer retryInterval = connectConfig.getRetryInterval();
            if (null != retryInterval) {
                singleServerConfig.setRetryAttempts(retryInterval);
            }
        }


        String authMode = connect.getConnect().getAuthMode();
        if (StringUtils.isNotBlank(authMode)) {
            switch (authMode) {
                case "none":
                    break;
                case "password":
                    ConnectPasswordBO connectPasswordBO = connect.getConnect().getPassword();
                    if (StringUtils.isNotBlank(connectPasswordBO.getPassword())) {
                        singleServerConfig.setPassword(connectPasswordBO.getPassword());
                    }
                    break;
                case "ssh":
                    //ssh证书逻辑
//                    ConnectSSHBO connectSSHBO=clusterBO.getConnect().getSsh();
//                    singleServerConfig.setSslEnableEndpointIdentification(true);
//                    singleServerConfig.setSslKeystorePassword(connectSSHBO.getPassword());
//                    singleServerConfig.setSslProvider()
                    break;
            }
        }
        redissonConfig.setStrategyList(clusterBO.getStrategyList());
        return redissonConfig;
    }
}

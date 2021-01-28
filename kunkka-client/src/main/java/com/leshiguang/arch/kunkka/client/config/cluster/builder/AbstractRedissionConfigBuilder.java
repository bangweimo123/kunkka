package com.leshiguang.arch.kunkka.client.config.cluster.builder;

import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterConnectParamsBO;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.scaffold.common.utils.AppUtil;
import org.redisson.config.BaseMasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-26 20:26
 * @Description
 */
public abstract class AbstractRedissionConfigBuilder {

    protected String processAddress(ConnectBO connect) {
        return "redis://" + connect.getHostName() + ":" + connect.getPort();
    }

    protected void processClusterConnectParams(BaseMasterSlaveServersConfig serversConfig, ClusterConnectParamsBO params) {
        if (params.getSupportClient()) {
            serversConfig.setClientName(AppUtil.appName());
        }
        serversConfig.setMasterConnectionPoolSize(params.getMaxTotal());
        serversConfig.setMasterConnectionMinimumIdleSize(params.getMinIdle());
        serversConfig.setSlaveConnectionPoolSize(params.getMaxTotal());
        serversConfig.setSlaveConnectionMinimumIdleSize(params.getMinIdle());
        serversConfig.setConnectTimeout(params.getConnectTimeout().intValue());
    }

    protected void processStandardConnectParams(SingleServerConfig serverConfig, ClusterConnectParamsBO params) {
        if (params.getSupportClient()) {
            serverConfig.setClientName(AppUtil.appName());
        }
        serverConfig.setConnectionMinimumIdleSize(params.getMinIdle());
        serverConfig.setConnectionPoolSize(params.getMaxTotal());
        serverConfig.setConnectTimeout(params.getConnectTimeout().intValue());
    }
}

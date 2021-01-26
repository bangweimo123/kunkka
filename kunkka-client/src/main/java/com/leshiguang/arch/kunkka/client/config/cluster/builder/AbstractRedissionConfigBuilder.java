package com.leshiguang.arch.kunkka.client.config.cluster.builder;

import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterConnectParamsBO;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import org.redisson.config.BaseMasterSlaveServersConfig;

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
        serversConfig.setMasterConnectionPoolSize(params.getMaxTotal());
        serversConfig.setMasterConnectionMinimumIdleSize(params.getMinIdle());
        serversConfig.setSlaveConnectionPoolSize(params.getMaxTotal());
        serversConfig.setSlaveConnectionMinimumIdleSize(params.getMinIdle());
        serversConfig.setConnectTimeout(params.getConnectTimeout().intValue());
    }
}

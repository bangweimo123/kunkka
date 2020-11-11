package com.leshiguang.redissonx.common.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-11-10 20:44
 * @Description
 */
public interface AuthStrategyConfigListenable {
    void addAuthStrategoyConfigListener(String clusterName, IZkDataListener listener);
}

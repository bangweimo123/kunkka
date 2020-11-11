package com.leshiguang.redissonx.common.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-11-10 20:48
 * @Description
 */
public interface ConnectConfigListenable {
    void addConnectConfigListener(String clusterName, IZkDataListener listener);
}

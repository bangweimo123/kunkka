package com.leshiguang.redissonx.common.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-16 15:04
 * @Modify
 */
public interface CategoryConfigListenable {
    void addCategoryConfigListener(String clusterName, String category, IZkDataListener listener);
}

package com.leshiguang.arch.redissonx.config.store;

import com.leshiguang.arch.redissonx.config.zookeeper.StoreConfigClient;
import com.leshiguang.arch.redissonx.config.zookeeper.ZkStoreConfigClient;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-13 18:09
 * @Modify
 */
public class StoreCategoryConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreCategoryConfigManager.class);
    private String cluterName;
    private Map<String, StoreCategoryConfig> localStoreCategoryConfigMap = new ConcurrentHashMap<String, StoreCategoryConfig>();
    private StoreConfigClient storeConfigClient = new ZkStoreConfigClient();

    public StoreCategoryConfigManager(String clusterName) {
        this.cluterName = clusterName;
    }

    public StoreCategoryConfig getStoreCategoryConfig(String category) {
        StoreCategoryConfig _exist = localStoreCategoryConfigMap.get(category);
        if (null == _exist) {
            synchronized (this) {
                _exist = localStoreCategoryConfigMap.get(category);
                if (null == _exist) {
                    _exist = storeConfigClient.getStoreCategoryConfig(cluterName, category);
                    storeConfigClient.addCategoryConfigListener(cluterName, category, new IZkDataListener() {
                        @Override
                        public void handleDataChange(String s, Object o) throws Exception {
                            StoreCategoryConfig data = (StoreCategoryConfig) o;
                            localStoreCategoryConfigMap.put(data.getCategory(), data);
                        }

                        @Override
                        public void handleDataDeleted(String s) throws Exception {
                            localStoreCategoryConfigMap.remove(s);
                        }
                    });
                    if (null != _exist) {
                        localStoreCategoryConfigMap.put(category, _exist);
                    } else {
                        LOGGER.error("can't find category config for configserver!");
                    }
                }
            }
        }
        return _exist;
    }
}

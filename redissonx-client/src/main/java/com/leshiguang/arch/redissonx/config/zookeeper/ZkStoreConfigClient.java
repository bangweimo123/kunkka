package com.leshiguang.arch.redissonx.config.zookeeper;

import com.leshiguang.arch.redissonx.config.hotkey.HotKeyStrategyAnalyzer;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.arch.redissonx.exception.StoreConfigException;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientFactory;
import org.I0Itec.zkclient.IZkDataListener;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-16 13:20
 * @Modify
 */
public class ZkStoreConfigClient implements StoreConfigClient {
    private ZookeeperClient zookeeperClient = ZookeeperClientFactory.getDefaultInstance();

    @Override
    public StoreCategoryConfig getStoreCategoryConfig(String clusterName, String category) {
        CategoryBO categoryBO = zookeeperClient.getCategory(clusterName, category);
        if (null == categoryBO) {
            throw new StoreConfigException("category not exist for cluster:" + clusterName + ",category:" + category);
        }
        StoreCategoryConfig storeCategoryConfig = new StoreCategoryConfig();
        storeCategoryConfig.setCategory(categoryBO.getCategory());
        storeCategoryConfig.setIndexTemplate(categoryBO.getIndexTemplate());
        storeCategoryConfig.setHot(categoryBO.isHot());
        storeCategoryConfig.setDuration(categoryBO.getDuration());
        if (categoryBO.isHot()) {
            //设置默认值
            HotKeyStrategyAnalyzer.analyze(storeCategoryConfig, categoryBO);
        }
        return storeCategoryConfig;
    }

    @Override
    public void addCategoryConfigListener(String clusterName, String category, IZkDataListener listener) {
        zookeeperClient.addCategoryConfigListener(clusterName, category, listener);
    }
}

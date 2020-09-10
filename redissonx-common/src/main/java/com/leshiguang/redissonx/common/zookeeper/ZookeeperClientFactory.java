package com.leshiguang.redissonx.common.zookeeper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-09 19:37
 * @Description
 */
public class ZookeeperClientFactory {
    private ZookeeperClient defaultClient;

    private static Map<String, ZookeeperClient> clientHolder = new ConcurrentHashMap<>();
    private Config config = ConfigService.getAppConfig();

    private ZookeeperClientFactory() {
        this.init();
    }

    public void init() {
        defaultClient = new ZookeeperClientImpl();
        String regionZkMappings = config.getProperty("zk.address", "");
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent configChangeEvent) {
                if (configChangeEvent.isChanged("zk.address")) {
                    reloadConfig(configChangeEvent.getChange("zk.address").getNewValue());
                }
            }
        });
        reloadConfig(regionZkMappings);
    }

    public static void reloadConfig(String regionZkMappings) {
        if (StringUtils.isNotBlank(regionZkMappings)) {
            JSONObject jsonObject = JSON.parseObject(regionZkMappings);
            for (String key : jsonObject.keySet()) {
                ZookeeperConfig zookeeperConfig = new ZookeeperConfig();
                zookeeperConfig.setAddress(jsonObject.getString(key));
                ZookeeperClient client = new ZookeeperClientImpl(key, zookeeperConfig);
                clientHolder.put(key, client);
            }
        }
    }


    public ZookeeperClient getInstanceByRegion(String region) {
        return clientHolder.get(region);
    }

    private ZookeeperClient getDefault() {
        return defaultClient;
    }

    private static class Holder {
        private static ZookeeperClientFactory INSTANCE = new ZookeeperClientFactory();

    }

    public static ZookeeperClient getInstance(String region) {
        return ZookeeperClientFactory.Holder.INSTANCE.getInstanceByRegion(region);
    }

    /**
     * 获取当前区域的zk
     *
     * @return
     */
    public static ZookeeperClient getDefaultInstance() {
        return ZookeeperClientFactory.Holder.INSTANCE.getDefault();
    }
}

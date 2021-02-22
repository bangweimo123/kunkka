package com.leshiguang.arch.kunkka.web.client.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.leshiguang.arch.kunkka.client.configure.IConfigureClient;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ConfigureClientFactory;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ZKConfig;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ZKConfigureClient;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bangwei.mo
 * @Date 2021-01-25 17:18
 * @Description
 */
@Component
public class KunkkaWebConfigureClientFactory implements ConfigureClientFactory, InitializingBean {
    private Map<String, IConfigureClient> configureClientMap = new ConcurrentHashMap<>();
    private static final String ZK_REGIONS_CONFIG = "zk.regions.config";
    private Config config = ConfigService.getAppConfig();

    @Override
    public IConfigureClient getInstance(String region) {
        return configureClientMap.get(region);
    }

    @Override
    public void start() {
        String regionConfigs = config.getProperty(ZK_REGIONS_CONFIG, "");
        this.prepare(regionConfigs);
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent configChangeEvent) {
                if (configChangeEvent.isChanged(ZK_REGIONS_CONFIG)) {
                    prepare(configChangeEvent.getChange(ZK_REGIONS_CONFIG).getNewValue());
                }
            }
        });
    }

    private void prepare(String regionConfigs) {
        if (StringUtils.isNotBlank(regionConfigs)) {
            Map<String, ZKConfig> configMap = JSON.parseObject(regionConfigs, new TypeReference<Map<String, ZKConfig>>() {
            });
            if (MapUtils.isNotEmpty(configMap)) {
                for (Map.Entry<String, ZKConfig> configEntry : configMap.entrySet()) {
                    IConfigureClient configureClient = new ZKConfigureClient(configEntry.getKey(), configEntry.getValue());
                    configureClient.start();
                    this.configureClientMap.put(configEntry.getKey(), configureClient);
                }
            }
        }
    }

    @Override
    public void stop() {
        if (MapUtils.isNotEmpty(configureClientMap)) {
            for (Map.Entry<String, IConfigureClient> configureClientEntry : configureClientMap.entrySet()) {
                configureClientEntry.getValue().stop();
            }
            configureClientMap.clear();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }
}

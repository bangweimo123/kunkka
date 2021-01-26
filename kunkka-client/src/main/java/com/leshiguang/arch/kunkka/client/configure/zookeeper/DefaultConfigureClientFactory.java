package com.leshiguang.arch.kunkka.client.configure.zookeeper;

import com.leshiguang.arch.kunkka.client.configure.IConfigureClient;
import com.leshiguang.arch.kunkka.client.exception.KunkkaConfigException;
import com.leshiguang.scaffold.common.utils.RegionUtil;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-25 17:05
 * @Description 只获取当前区域的配置
 */
public class DefaultConfigureClientFactory implements ConfigureClientFactory {

    private ZKConfigLoader zkConfigLoader;

    private IConfigureClient configureClient;

    public DefaultConfigureClientFactory() {
        this.start();
    }

    @Override
    public void start() {
        zkConfigLoader = new ApolloZKConfigLoader();
        ZKConfig defaultZKConfig = zkConfigLoader.load();
        configureClient = new ZKConfigureClient(RegionUtil.getRegionKey(), defaultZKConfig);
        configureClient.start();
    }

    @Override
    public void stop() {
        configureClient.stop();
    }

    public IConfigureClient getInstance(String region) {
        if (RegionUtil.getRegionKey().equalsIgnoreCase(region)) {
            return configureClient;
        } else {
            throw new KunkkaConfigException(String.format("DefaultConfigureClientFactory Online Support Current Region In Apollo,currentRegion:%s,wantRegion:%s", RegionUtil.getRegionKey(), region));
        }
    }
}

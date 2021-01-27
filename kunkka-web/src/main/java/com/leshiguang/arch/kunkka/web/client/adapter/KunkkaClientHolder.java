package com.leshiguang.arch.kunkka.web.client.adapter;

import com.leshiguang.arch.kunkka.client.DefaultKunkkaClient;
import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.configure.IConfigCallback;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ConfigureClientFactory;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-27 20:22
 * @Description
 */
@Component
public class KunkkaClientHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(KunkkaClientHolder.class);
    @Resource
    private ConfigureClientFactory kunkkaWebConfigureClientFactory;
    private Map<String, KunkkaClient> clientMap = new ConcurrentHashMap<String, KunkkaClient>();

    public KunkkaClient getClientByClusterName(String clusterName, String region) {
        String key = clusterName + "_" + region;
        if (!clientMap.containsKey(key)) {
            synchronized (this) {
                if (!clientMap.containsKey(key)) {
                    try {
                        DefaultKunkkaClient kunkkaClient = new DefaultKunkkaClient(clusterName, region);
                        kunkkaClient.setConfigureClientFactory(kunkkaWebConfigureClientFactory);
                        kunkkaClient.setClusterChangedCallback(new IConfigCallback<ClusterBO>() {
                            @Override
                            public void changed(ClusterBO newData) {
                                clientMap.remove(key);
                            }

                            @Override
                            public void deleted() {
                                clientMap.remove(key);

                            }
                        });
                        kunkkaClient.start();
                        clientMap.put(key, kunkkaClient);
                    } catch (Exception e) {
                        LOGGER.error("exception for create KunkkaClient", e);
                    }
                }
            }
        }
        return clientMap.get(key);
    }
}

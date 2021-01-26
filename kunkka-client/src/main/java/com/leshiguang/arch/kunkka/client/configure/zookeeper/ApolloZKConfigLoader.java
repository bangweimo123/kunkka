package com.leshiguang.arch.kunkka.client.configure.zookeeper;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.leshiguang.arch.kunkka.client.configure.IConfigCallback;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-22 17:16
 * @Description
 */
public class ApolloZKConfigLoader implements ZKConfigLoader {
    private static final String APOLLO_NAMESPACE = "lx-arch.kunkka";

    public static final String ZK_ADDRESS = "zk.address";

    public static final String ZK_SESSION_TIMEOUT = "zk.session.timeout";

    public static final String ZK_CONNECTION_TIMEOUT = "zk.connection.timeout";

    public static final String ZK_OPERATION_RETRY_TIMEOUT = "zk.operation.retry.timeout";
    private static Config config = ConfigService.getConfig(APOLLO_NAMESPACE);

    private ZKConfig zkConfig;

    private IConfigCallback callback;

    public ApolloZKConfigLoader() {
        this.prepareZKConfig();
        config.addChangeListener(new ZKConfigChangeListener());
    }

    public void prepareZKConfig() {
        ZKConfig zkConfig = new ZKConfig();
        zkConfig.setAddress(config.getProperty(ZK_ADDRESS, ""));
        zkConfig.setConnectTimeout(config.getIntProperty(ZK_CONNECTION_TIMEOUT, zkConfig.getConnectTimeout()));
        zkConfig.setSessionTimeout(config.getIntProperty(ZK_SESSION_TIMEOUT, zkConfig.getSessionTimeout()));
        zkConfig.setOperationRetryTimeout(config.getLongProperty(ZK_OPERATION_RETRY_TIMEOUT, zkConfig.getOperationRetryTimeout()));
        this.zkConfig = zkConfig;
    }

    @Override
    public ZKConfig load() {
        return zkConfig;
    }

    @Override
    public void addCallback(IConfigCallback callback) {
        this.callback = callback;
    }

    class ZKConfigChangeListener implements ConfigChangeListener {

        @Override
        public void onChange(ConfigChangeEvent configChangeEvent) {
            for (String changeKey : configChangeEvent.changedKeys()) {
                if (changeKey.startsWith("zk")) {
                    prepareZKConfig();
                    if (changeKey.equalsIgnoreCase(ZK_ADDRESS)) {
                        callback.changed(zkConfig);
                    }
                }
            }
        }
    }
}

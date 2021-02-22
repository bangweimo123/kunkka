package com.leshiguang.arch.kunkka.client.config.cluster;

import com.leshiguang.arch.kunkka.client.configure.IConfigCallback;
import com.leshiguang.arch.kunkka.client.configure.IConfigureClient;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ConfigureClientFactory;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bangwei.mo
 * @Date 2021-01-13 10:48
 * @Description
 */
public class ZkClusterConfigManager implements ClusterConfigManager {
    private Map<HolderKey, ClusterBO> localStoreClusterConfigMap = new ConcurrentHashMap<>();

    @Override
    public ClusterBO getClusterConfig(String clusterName, String region, ConfigureClientFactory configureClientFactory) {
        HolderKey key = new HolderKey(clusterName, region);
        if (!localStoreClusterConfigMap.containsKey(key)) {
            synchronized (this) {
                if (!localStoreClusterConfigMap.containsKey(key)) {
                    IConfigureClient configureClient = configureClientFactory.getInstance(region);
                    ClusterBO cluster = configureClient.getCluster(clusterName);
                    configureClient.clusterWatch(clusterName, new IConfigCallback<ClusterBO>() {

                        @Override
                        public void changed(ClusterBO newData) {
                            localStoreClusterConfigMap.put(new HolderKey(newData.getClusterName(), region), newData);
                        }

                        @Override
                        public void deleted() {
                            localStoreClusterConfigMap.remove(new HolderKey(clusterName, region));
                        }
                    });
                    localStoreClusterConfigMap.put(key, cluster);
                }
            }
        }
        return localStoreClusterConfigMap.get(key);
    }

    public class HolderKey {
        private String clusterName;

        private String region;

        public HolderKey(String clusterName, String region) {
            this.clusterName = clusterName;
            this.region = region;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            HolderKey holderKey = (HolderKey) o;

            return new EqualsBuilder()
                    .append(clusterName, holderKey.clusterName)
                    .append(region, holderKey.region)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(clusterName)
                    .append(region)
                    .toHashCode();
        }
    }
}

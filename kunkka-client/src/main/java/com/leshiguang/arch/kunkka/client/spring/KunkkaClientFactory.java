package com.leshiguang.arch.kunkka.client.spring;

import com.leshiguang.arch.kunkka.client.DefaultKunkkaClient;
import com.leshiguang.arch.kunkka.client.KunkkaClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Author bangwei.mo
 * @Date 2021-01-26 18:08
 * @Description
 */
public class KunkkaClientFactory implements FactoryBean<KunkkaClient>, InitializingBean, DisposableBean {
    private String clusterName;
    private KunkkaClient kunkkaClient;

    public KunkkaClientFactory(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public void destroy() throws Exception {
        ((DefaultKunkkaClient) kunkkaClient).stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        kunkkaClient = new DefaultKunkkaClient(clusterName);
        ((DefaultKunkkaClient) kunkkaClient).start();
    }

    @Override
    public KunkkaClient getObject() throws Exception {
        return kunkkaClient;
    }

    @Override
    public Class<?> getObjectType() {
        return kunkkaClient.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

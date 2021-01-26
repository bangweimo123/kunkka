package com.leshiguang.arch.kunkka.client.configure.zookeeper;

import com.leshiguang.arch.kunkka.client.configure.IConfigureClient;
import com.leshiguang.arch.kunkka.client.lifecycle.Lifecycle;
import com.leshiguang.scaffold.common.utils.RegionUtil;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-09 19:37
 * @Description
 */
public interface ConfigureClientFactory extends Lifecycle {
    IConfigureClient getInstance(String region);
}

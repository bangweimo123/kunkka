package com.leshiguang.arch.kunkka.client.configure.zookeeper;

import com.leshiguang.arch.kunkka.client.configure.IConfigListener;

/**
 * @Author bangwei.mo
 * @Date 2021-01-22 17:15
 * @Description
 */
public interface ZKConfigLoader extends IConfigListener {
    /**
     * 基于当前区域获取
     * @return
     */
    ZKConfig load();
}

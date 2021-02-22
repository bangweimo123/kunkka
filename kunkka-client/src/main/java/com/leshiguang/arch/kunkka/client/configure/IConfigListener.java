package com.leshiguang.arch.kunkka.client.configure;

/**
 * @Author bangwei.mo
 * @Date 2021-01-22 17:05
 * @Description
 */
public interface IConfigListener {
    /**
     * 添加监听器
     *
     * @return
     */
    void addCallback(IConfigCallback callback);
}

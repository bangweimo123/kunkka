package com.leshiguang.arch.kunkka.client.configure;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-22 17:01
 * @Description
 */
public interface IConfigCallback<T> {
    /**
     * 变更
     */
    void changed(T newData);

    /**
     * 删除
     */
    void deleted();
}

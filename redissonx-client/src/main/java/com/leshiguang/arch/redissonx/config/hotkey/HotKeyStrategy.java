package com.leshiguang.arch.redissonx.config.hotkey;

import com.leshiguang.redissonx.common.entity.category.HotKeyStrategyBO;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-30 13:59
 * @Modify
 */
public interface HotKeyStrategy {
    /**
     * 执行 parse
     *
     * @param strategyBO
     * @return
     */
    HotKeyStrategy parse(HotKeyStrategyBO strategyBO);

    /**
     * @return
     */
    String getName();

    /**
     * 策略执行
     */
    void process();
}

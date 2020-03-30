package com.leshiguang.arch.redissonx.config.hotkey;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-30 13:59
 * @Modify
 */
public interface HotKeyStrategy {

    public static final String LOCAL = "local";
    public static final String FLOW_CONTROL = "flow_control";

    String getName();

    /**
     * 策略执行
     */
    void process();
}

package com.leshiguang.arch.redissonx.config.hotkey;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-30 17:35
 * @Modify
 */
public enum HotKeyStrategyEnum {
    LOCAL("local", LocalCacheHotKeyStrategy.class),

    FLOW_CONTROL("flow_control", FlowControlHotKeyStrategy.class);

    HotKeyStrategyEnum(String strategy, Class strategyClass) {
        this.strategy = strategy;
        this.strategyClass = strategyClass;
    }

    private String strategy;

    private Class strategyClass;

    public static Class<HotKeyStrategy> getClazz(String strategy) {
        for (HotKeyStrategyEnum enumItem : HotKeyStrategyEnum.values()) {
            String define_strategy = enumItem.strategy;
            if (strategy.equalsIgnoreCase(define_strategy)) {
                return enumItem.strategyClass;
            }
        }
        return null;
    }
}

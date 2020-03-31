package com.leshiguang.redissonx.common.entity.application;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-31 10:58
 * @Modify
 */
@Data
public class ApplicationStrategyBO implements Serializable {
    /**
     * 策略名
     */
    private String strategy;
    /**
     * 策略参数
     */
    private Map<String, Object> params;
}

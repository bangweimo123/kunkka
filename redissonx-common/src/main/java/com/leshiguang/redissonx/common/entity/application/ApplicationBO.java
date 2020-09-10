package com.leshiguang.redissonx.common.entity.application;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-30 18:42
 * @Modify
 */
@Data
public class ApplicationBO implements Serializable {
    /**
     * 应用名
     */
    private String application;

    private List<ApplicationStrategyBO> strategyList;
}

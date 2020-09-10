package com.leshiguang.redissonx.common.entity.cluster;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-02 21:00
 * @Description
 */
@Data
public class ClusterStrategyBO implements Serializable {
    private String source;

    private String operator;

    private Object data;
}

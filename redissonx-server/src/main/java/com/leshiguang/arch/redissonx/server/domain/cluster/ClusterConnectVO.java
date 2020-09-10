package com.leshiguang.arch.redissonx.server.domain.cluster;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-09 19:59
 * @Description
 */
@Data
public class ClusterConnectVO implements Serializable {
    private String region;

    private String connectName;

    private Integer database;
}

package com.leshiguang.arch.redissonx.server.domain.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 14:12
 * @Modify
 */
@Data
public class ClusterQueryRequest implements Serializable {
    private String userId;

    private String keyword;

    private String mode;

    private String tenant;

    private String application;

    private List<Integer> statusList;
}

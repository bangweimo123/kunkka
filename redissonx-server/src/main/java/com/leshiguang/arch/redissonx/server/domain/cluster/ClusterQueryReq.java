package com.leshiguang.arch.redissonx.server.domain.cluster;

import com.leshiguang.redissonx.common.base.RedissonxPaging;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 15:27
 * @Modify
 */
@Data
public class ClusterQueryReq implements Serializable {
    private RedissonxPaging paging;

    private String userId;

    private String keyword;

    private String mode;

    private String tenant;

    private String application;
}

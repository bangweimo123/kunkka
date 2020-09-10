package com.leshiguang.arch.redissonx.server.domain.connect;

import com.leshiguang.redissonx.common.base.RedissonxPaging;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 16:47
 * @Modify
 */
@Data
public class ConnectQueryReq implements Serializable {
    private RedissonxPaging paging;

    private String region;

    private String address;

    private String keyword;
}

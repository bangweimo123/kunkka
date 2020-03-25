package com.leshiguang.arch.redissonx.server.domain.rediskey;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-25 13:32
 * @Modify
 */
@Data
public class RedisKeyValueVO implements Serializable {
    private Object data;

    private Long remainTimeToLive;
}

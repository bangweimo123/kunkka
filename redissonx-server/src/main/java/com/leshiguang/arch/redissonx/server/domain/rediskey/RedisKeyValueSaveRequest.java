package com.leshiguang.arch.redissonx.server.domain.rediskey;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-25 14:29
 * @Modify
 */
@Data
public class RedisKeyValueSaveRequest implements Serializable {
    private String category;

    private List<String> params;

    private MValueBO value;
}

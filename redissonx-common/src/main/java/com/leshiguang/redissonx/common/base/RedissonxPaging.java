package com.leshiguang.redissonx.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2019-10-15 19:30
 * @Modify
 */
@Data
public class RedissonxPaging implements Serializable {
    private Integer pageSize;

    private Integer pageIndex;
}

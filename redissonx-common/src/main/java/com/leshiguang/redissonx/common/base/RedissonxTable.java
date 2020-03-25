package com.leshiguang.redissonx.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2019-10-14 21:05
 * @Modify
 */
@Data
public class RedissonxTable<T> implements Serializable {

    private Integer pageSize;

    private Integer pageIndex;

    private List<T> data;

    private Long totalCount;

    public RedissonxTable(Integer pageSize, Integer pageIndex, List<T> data) {
        this(pageSize, pageIndex, data, null==data?0l:new Long(data.size()));
    }

    public RedissonxTable(Integer pageSize, Integer pageIndex, List<T> data, Long totalCount) {
        this.data = data;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalCount = totalCount;
    }
}

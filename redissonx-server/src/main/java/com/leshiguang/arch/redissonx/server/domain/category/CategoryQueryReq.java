package com.leshiguang.arch.redissonx.server.domain.category;

import com.leshiguang.redissonx.common.base.RedissonxPaging;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-23 19:18
 * @Modify
 */
@Data
public class CategoryQueryReq implements Serializable {
    private RedissonxPaging paging;

    private String keyword;


    private String userId;

    private Boolean showHotKey;

    private List<Integer> statusList;
}

package com.leshiguang.redissonx.common.entity.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-23 19:21
 * @Modify
 */
@Data
public class CategoryQueryRequest implements Serializable {
    private String clusterName;

    private String keyword;

    private List<Integer> statusList;

    private String userId;

    private Boolean showHotKey;
}

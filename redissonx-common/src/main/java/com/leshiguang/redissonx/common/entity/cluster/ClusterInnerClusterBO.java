package com.leshiguang.redissonx.common.entity.cluster;

import lombok.Data;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 14:08
 * @Modify
 */
@Data
public class ClusterInnerClusterBO extends ClusterBO {
    private List<String> addresses;

    private Integer scanInterval;
}

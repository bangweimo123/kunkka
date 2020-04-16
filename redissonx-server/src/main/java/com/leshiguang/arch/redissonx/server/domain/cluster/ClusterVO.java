package com.leshiguang.arch.redissonx.server.domain.cluster;

import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-23 16:30
 * @Modify
 */
@Data
public class ClusterVO extends ClusterBO {
    private List<String> ownerList;

    private List<String> memberList;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String connectName;
}

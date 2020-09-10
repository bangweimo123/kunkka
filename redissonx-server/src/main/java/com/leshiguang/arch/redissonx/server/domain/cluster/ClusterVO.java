package com.leshiguang.arch.redissonx.server.domain.cluster;

import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterConnectBO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-23 16:30
 * @Modify
 */
@Data
public class ClusterVO implements Serializable {
    /**
     * 集群名
     */
    private String clusterName;
    /**
     * 模式 single/cluster/sentinel/replicate/masterslave
     */
    private String mode;
    /**
     * 连接列表
     */
    private List<ClusterConnectVO> connectList;
    /**
     * 租户列表
     */
    private List<Integer> tenantList;
    /**
     * 应用列表
     */
    private List<String> applicationList;
    /**
     * owner
     */
    private List<String> ownerList;
    /**
     * member
     */
    private List<String> memberList;
    /**
     * 创建失败
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态
     */
    private Integer status;
}

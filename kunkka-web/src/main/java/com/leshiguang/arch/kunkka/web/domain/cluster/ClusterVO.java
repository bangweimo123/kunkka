package com.leshiguang.arch.kunkka.web.domain.cluster;

import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterSimpleBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2020-03-23 16:30
 * @Modify
 */
@Getter
@Setter
public class ClusterVO extends ClusterSimpleBO implements Serializable {
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
    /**
     *
     */
    private List<String> regionList;
    /**
     * 成员
     */
    private List<String> memberList;
    /**
     * owner
     */
    private List<String> ownerList;
}

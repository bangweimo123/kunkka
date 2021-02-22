package com.leshiguang.arch.kunkka.web.domain.connect;

import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2020-04-15 15:34
 * @Modify
 */
@Getter
@Setter
public class ConnectVO extends ConnectBO {
    /**
     * 一个连接对应的集群列表
     */
    private List<String> clusterList;

    private String region;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}

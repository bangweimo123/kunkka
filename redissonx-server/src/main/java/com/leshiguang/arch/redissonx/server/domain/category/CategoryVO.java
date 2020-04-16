package com.leshiguang.arch.redissonx.server.domain.category;

import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-24 11:49
 * @Modify
 */
@Data
public class CategoryVO extends CategoryBO {
    private List<String> ownerList;

    private List<String> memberList;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}

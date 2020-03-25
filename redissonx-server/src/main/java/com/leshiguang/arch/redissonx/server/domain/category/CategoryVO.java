package com.leshiguang.arch.redissonx.server.domain.category;

import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import lombok.Data;

import java.util.Date;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-24 11:49
 * @Modify
 */
@Data
public class CategoryVO extends CategoryBO {
    private String operator;

    private String creator;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}

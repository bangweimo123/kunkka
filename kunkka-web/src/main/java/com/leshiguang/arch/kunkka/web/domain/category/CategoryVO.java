package com.leshiguang.arch.kunkka.web.domain.category;

import com.leshiguang.arch.kunkka.common.entity.category.CategoryBO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2020-03-24 11:49
 * @Modify
 */
@Getter
@Setter
public class CategoryVO extends CategoryBO {
    /**
     * id
     */
    private Integer categoryId;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    /**
     * 此category对应的cluster的区域列表
     */
    private List<String> regionList;
    /**
     * 是否有权限
     */
    private Boolean hasPrivilege = Boolean.FALSE;
}

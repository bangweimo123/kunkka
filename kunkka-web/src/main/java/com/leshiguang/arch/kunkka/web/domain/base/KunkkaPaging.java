package com.leshiguang.arch.kunkka.web.domain.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2019-10-15 19:30
 * @Modify
 */
@Data
public class KunkkaPaging implements Serializable {
    private Integer pageSize;

    private Integer pageIndex;
}

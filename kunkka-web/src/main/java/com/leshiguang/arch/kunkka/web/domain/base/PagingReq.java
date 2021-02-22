package com.leshiguang.arch.kunkka.web.domain.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-14 11:57
 * @Description
 */
@Getter
@Setter
public class PagingReq implements Serializable {
    private KunkkaPaging paging;
}

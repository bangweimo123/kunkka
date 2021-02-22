package com.leshiguang.arch.kunkka.web.operate.log;

import com.leshiguang.arch.kunkka.web.domain.base.PagingReq;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-22 11:27
 * @Description
 */
@Getter
@Setter
public class OperateLogQueryReq extends PagingReq implements Serializable {
    private String type;

    private String data;
}

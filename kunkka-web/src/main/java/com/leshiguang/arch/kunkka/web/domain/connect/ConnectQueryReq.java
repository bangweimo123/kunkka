package com.leshiguang.arch.kunkka.web.domain.connect;

import com.leshiguang.arch.kunkka.web.domain.base.PagingReq;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2020-04-15 16:47
 * @Modify
 */
@Getter
@Setter
public class ConnectQueryReq extends PagingReq implements Serializable {
    private String address;

    private String region;

    private String keyword;
}

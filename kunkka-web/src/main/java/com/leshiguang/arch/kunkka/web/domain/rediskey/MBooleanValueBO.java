package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-02-03 20:03
 * @Description
 */
@Getter
@Setter
public class MBooleanValueBO implements Serializable {
    private Boolean value;

    private Long offset;
}

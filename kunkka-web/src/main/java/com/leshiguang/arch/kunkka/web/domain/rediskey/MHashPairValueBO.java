package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-20 19:09
 * @Description
 */
@Getter
@Setter
public class MHashPairValueBO implements Serializable {
    private MValueBO key;

    private MValueBO value;
}

package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author bangwei.mo
 * @Date 2021-01-20 18:03
 * @Description
 */
@Getter
@Setter
public class MListValueBO extends AbstractMultiValueBO {
    /**
     * lpush / rpush
     */
    private String option;
}

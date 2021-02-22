package com.leshiguang.arch.kunkka.web.domain.rediskey;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-20 18:53
 * @Description
 */
public interface AbstractValueBO extends Serializable {
    public <T> T parse();
}

package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2020-03-25 13:32
 * @Modify
 */
@Getter
@Setter
public class RedisKeyValueVO implements Serializable {
    private Object data;

    private Long remainTimeToLive;
}

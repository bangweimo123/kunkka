package com.leshiguang.arch.redissonx.server.domain.connect;

import com.leshiguang.redissonx.common.entity.connect.ConnectBO;
import lombok.Data;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 15:34
 * @Modify
 */
@Data
public class ConnectVO extends ConnectBO {
    private String address;
}

package com.leshiguang.arch.redissonx.server.utils;


import com.leshiguang.arch.redissonx.core.entity.gen.Connect;
import com.leshiguang.arch.redissonx.server.domain.connect.ConnectVO;
import com.leshiguang.redissonx.common.entity.connect.ConnectBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-02 20:28
 * @Description
 */
public class Transfer {
    @Mapper
    public interface ConnectTransfer {
        ConnectTransfer INSTANCE = Mappers.getMapper(ConnectTransfer.class);

        @Mappings({
                @Mapping(source = "validityEnd", target = "validityEnd", dateFormat = "yyyy年MM月dd日 HH:mm:ss"),
                @Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy年MM月dd日 HH:mm:ss")
        })
        ConnectVO convert(ConnectBO core);

        ConnectVO convert2(Connect core);

        List<ConnectVO> convert(List<ConnectBO> cores);

        List<ConnectVO> convert2(List<Connect> cores);
    }
}

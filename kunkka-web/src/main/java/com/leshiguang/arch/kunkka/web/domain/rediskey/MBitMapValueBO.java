package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-02-03 20:03
 * @Description
 */
@Getter
@Setter
public class MBitMapValueBO implements AbstractValueBO {
    private List<MBooleanValueBO> values;

    @Override
    public <T> T parse() {
        Map<Long, Boolean> bitMap = new HashMap<>();
        for (MBooleanValueBO booleanValue : values) {
            bitMap.put(booleanValue.getOffset(), booleanValue.getValue());
        }
        return (T) bitMap;
    }
}

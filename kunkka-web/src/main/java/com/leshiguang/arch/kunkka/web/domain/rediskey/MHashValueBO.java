package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-20 19:07
 * @Description
 */
@Getter
@Setter
public class MHashValueBO implements AbstractValueBO {
    private List<MHashPairValueBO> values;

    @Override
    public <T> T parse() {
        Map map = new HashMap<>();
        for (MHashPairValueBO value : values) {
            map.put(value.getKey().parse(), value.getValue().parse());
        }
        return (T) map;

    }
}

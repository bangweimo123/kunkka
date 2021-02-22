package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2021-01-20 18:57
 * @Description
 */

@Getter
@Setter
public class AbstractMultiValueBO implements AbstractValueBO {
    private List<MValueBO> values;

    @Override
    public <T> T parse() {
        Serializable[] results = new Serializable[values.size()];
        for (int i = 0; i < values.size(); i++) {
            results[i] = values.get(i).parse();
        }
        return (T) results;
    }
}

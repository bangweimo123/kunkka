package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author bangwei.mo
 * @Date 2021-01-20 18:47
 * @Description
 */

@Getter
@Setter
public class MZSetValueBO implements AbstractValueBO {
    private List<MScoreValueBO> values;

    @Override
    public <T> T parse() {
        Set<ZSetOperations.TypedTuple> typedTupleSet = new HashSet<>();
        for (MScoreValueBO scoreValue : values) {
            ZSetOperations.TypedTuple tuple = new DefaultTypedTuple(scoreValue.parse(), scoreValue.getScore());
            typedTupleSet.add(tuple);
        }
        return (T) typedTupleSet;
    }
}

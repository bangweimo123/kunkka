package org.springframework.data.redis.core;

import java.util.List;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-02-02 21:02
 * @Description
 */
public interface BoundBitMapOperations<K> extends BoundKeyOperations<K> {
    Boolean setBit(long offset, boolean tag);

    Boolean getBit(long offset);

    Map<Long, Boolean> setBits(Map<Long, Boolean> bitsMap);

    Map<Long, Boolean> getBits(List<Long> offsets);

    Map<Long, Boolean> getBits(Long start, Long end);

    Long bitCount();

    Long bitCount(long start, long end);
}

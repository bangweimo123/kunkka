package org.springframework.data.redis.core;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-02-02 21:02
 * @Description
 */
public interface BoundBitMapOperations<K> extends BoundKeyOperations<K> {
    Boolean setBit(long offset, boolean tag);

    Boolean getBit(long offset);

    Long count();
}

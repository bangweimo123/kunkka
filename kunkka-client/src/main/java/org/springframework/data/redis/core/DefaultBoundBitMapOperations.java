package org.springframework.data.redis.core;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-02-03 19:45
 * @Description
 */
public class DefaultBoundBitMapOperations<K> extends DefaultBoundKeyOperations<K> implements BoundBitMapOperations<K> {

    private static final BitFieldSubCommands.BitFieldType USIGN_1 = BitFieldSubCommands.BitFieldType.unsigned(1);
    private final ValueOperations<K, ?> ops;

    private final RedisOperations<K, ?> rOps;

    DefaultBoundBitMapOperations(K key, RedisOperations operations) {
        super(key, operations);
        this.rOps = operations;
        this.ops = operations.opsForValue();
    }

    @SuppressWarnings("unchecked")
    private byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");
        if (rOps.getKeySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }

        return ((RedisSerializer) rOps.getKeySerializer()).serialize(key);
    }

    @Override
    public Boolean setBit(long offset, boolean tag) {
        return ops.setBit(getKey(), offset, tag);
    }

    @Override
    public Boolean getBit(long offset) {
        return ops.getBit(getKey(), offset);
    }

    @Override
    public Map<Long, Boolean> setBits(Map<Long, Boolean> bitsMap) {
        final byte[] rawKey = rawKey(getKey());

        BitFieldSubCommands commands = BitFieldSubCommands.create();
        List<Long> offsetsOrder = new ArrayList<>(bitsMap.size());
        for (Map.Entry<Long, Boolean> bitsEntry : bitsMap.entrySet()) {
            offsetsOrder.add(bitsEntry.getKey());
            commands = commands.set(USIGN_1).valueAt(bitsEntry.getKey()).to(bitsEntry.getValue() ? 1 : 0);
        }
        final BitFieldSubCommands CMD = commands;
        List<Long> bits = rOps.execute(new RedisCallback<List<Long>>() {
            @Override
            public List<Long> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitField(rawKey, CMD);
            }
        });
        Map<Long, Boolean> results = new HashMap<>(capacity(bitsMap.size()));
        for (int index = 0; index < offsetsOrder.size(); index++) {
            results.put(offsetsOrder.get(index), bits.get(index) == 1L ? Boolean.TRUE : Boolean.FALSE);
        }
        return results;
    }


    public static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            return expectedSize + 1;
        }

        return (int) ((float) expectedSize / 0.75F + 1.0F);
    }

    @Override
    public Map<Long, Boolean> getBits(List<Long> offsets) {
        final byte[] rawKey = rawKey(getKey());
        BitFieldSubCommands commands = BitFieldSubCommands.create();
        for (Long offset : offsets) {
            commands = commands.get(USIGN_1).valueAt(offset);
        }
        final BitFieldSubCommands CMD = commands;
        List<Long> bits = rOps.execute(new RedisCallback<List<Long>>() {
            @Override
            public List<Long> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitField(rawKey, CMD);
            }
        });
        Map<Long, Boolean> results = new HashMap<>(capacity(offsets.size()));
        for (int index = 0; index < offsets.size(); index++) {
            results.put(offsets.get(index), bits.get(index) == 1L ? Boolean.TRUE : Boolean.FALSE);
        }
        return results;
    }

    @Override
    public Map<Long, Boolean> getBits(Long start, Long end) {
        List<Long> offsets = new ArrayList<>();
        for (Long i = start; i < end; i++) {
            offsets.add(i);
        }
        return getBits(offsets);
    }

    @Override
    public Long bitCount() {
        final byte[] rawKey = rawKey(getKey());
        return rOps.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(rawKey);
            }
        });
    }

    @Override
    public Long bitCount(long start, long end) {
        final byte[] rawKey = rawKey(getKey());
        return rOps.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(rawKey, start, end);
            }
        });
    }

    @Override
    public DataType getType() {
        return DataType.STRING;
    }
}

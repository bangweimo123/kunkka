package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
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
 * @Author bangwei.mo
 * @Date 2021-02-02 21:05
 * @Description
 */
public class KunkkaBoundBitMapOperations<K extends StoreKey> extends KunkkaBoundKeyOperations<K> implements BoundBitMapOperations<K> {
    private static final BitFieldSubCommands.BitFieldType SIGN_1 = BitFieldSubCommands.BitFieldType.signed(1);
    private static final BitFieldSubCommands.BitFieldType USIGN_1 = BitFieldSubCommands.BitFieldType.unsigned(1);
    private final ValueOperations<K, Object> ops;

    private final RedisOperations<K, Object> rOps;

    /**
     * Constructs a new {@code DefaultBoundGeoOperations}.
     *
     * @param key        must not be {@literal null}.
     * @param operations must not be {@literal null}.
     */
    KunkkaBoundBitMapOperations(CategoryConfig categoryConfig, K key, RedisOperations operations) {
        super(categoryConfig, key, operations);
        rOps = operations;
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
        return new MonitorCommand(MonitorMethod.create("setBit").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.setBit(getKey(), offset, tag));
    }

    @Override
    public Boolean getBit(long offset) {
        return new MonitorCommand(MonitorMethod.create("getBit"), getCategoryConfig()).execute(getKey(), () -> ops.getBit(getKey(), offset));
    }

    @Override
    public Map<Long, Boolean> setBits(final Map<Long, Boolean> bitsMap) {
        return new MonitorCommand(MonitorMethod.create("setBits").setExpireable(), getCategoryConfig()).execute(getKey(), () -> {
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
        });
    }


    public static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            return expectedSize + 1;
        }

        return (int) ((float) expectedSize / 0.75F + 1.0F);
    }

    @Override
    public Map<Long, Boolean> getBits(List<Long> offsets) {
        return new MonitorCommand(MonitorMethod.create("getBits"), getCategoryConfig()).execute(getKey(), () -> {
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
        });
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
        return new MonitorCommand(MonitorMethod.create("bitCount"), getCategoryConfig()).execute(getKey(), () -> rOps.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(rawKey);
            }
        }));
    }

    @Override
    public Long bitCount(long start, long end) {
        final byte[] rawKey = rawKey(getKey());
        return new MonitorCommand(MonitorMethod.create("bitCount"), getCategoryConfig()).execute(getKey(), () -> rOps.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(rawKey, start, end);
            }
        }));
    }

    @Override
    public DataType getType() {
        return DataType.STRING;
    }
}

//package org.springframework.data.redis.core;
//
//import com.leshiguang.arch.kunkka.client.StoreKey;
//import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
//import org.springframework.data.domain.Range;
//import org.springframework.data.redis.connection.DataType;
//import org.springframework.data.redis.connection.RedisZSetCommands;
//import org.springframework.data.redis.connection.stream.*;
//import org.springframework.lang.Nullable;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author bangwei.mo[bangwei.mo@lifesense.com]
// * @Date 2021-01-26 16:49
// * @Description
// */
//public class KunkkaBoundStreamOperations<K extends StoreKey, HK, HV> extends KunkkaBoundKeyOperations<K> implements BoundStreamOperations<K, HK, HV> {
//    private final StreamOperations<K, HK, HV> ops;
//
//    /**
//     * Constructs a new <code>DefaultBoundSetOperations</code> instance.
//     *
//     * @param key
//     * @param operations
//     */
//    KunkkaBoundStreamOperations(CategoryConfig categoryConfig, K key, RedisOperations<K, ?> operations) {
//        super(categoryConfig, key, operations);
//        this.ops = operations.opsForStream();
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#acknowledge(java.lang.String, java.lang.String[])
//     */
//    @Nullable
//    @Override
//    public Long acknowledge(String group, String... recordIds) {
//        return new MonitorCommand(MonitorMethod.create("acknowledge"), getCategoryConfig()).execute(getKey(), () -> ops.acknowledge(getKey(), group, recordIds));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#add(java.util.Map)
//     */
//    @Nullable
//    @Override
//    public RecordId add(Map<HK, HV> body) {
//        return new MonitorCommand(MonitorMethod.create("add"), getCategoryConfig()).execute(getKey(), () -> ops.add(getKey(), body));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#delete(java.lang.String[])
//     */
//    @Nullable
//    @Override
//    public Long delete(String... recordIds) {
//        return new MonitorCommand(MonitorMethod.create("delete"), getCategoryConfig()).execute(getKey(), () -> ops.delete(getKey(), recordIds));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#createGroup(org.springframework.data.redis.connection.RedisStreamCommands.ReadOffset, java.lang.String)
//     */
//    @Nullable
//    @Override
//    public String createGroup(ReadOffset readOffset, String group) {
//        return new MonitorCommand(MonitorMethod.create("createGroup"), getCategoryConfig()).execute(getKey(), () -> ops.createGroup(getKey(), readOffset, group));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#deleteConsumer(org.springframework.data.redis.connection.RedisStreamCommands.Consumer)
//     */
//    @Nullable
//    @Override
//    public Boolean deleteConsumer(Consumer consumer) {
//        return new MonitorCommand(MonitorMethod.create("deleteConsumer"), getCategoryConfig()).execute(getKey(), () -> ops.deleteConsumer(getKey(), consumer));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#destroyGroup(java.lang.String)
//     */
//    @Nullable
//    @Override
//    public Boolean destroyGroup(String group) {
//        return new MonitorCommand(MonitorMethod.create("destroyGroup"), getCategoryConfig()).execute(getKey(), () -> ops.destroyGroup(getKey(), group));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#size()
//     */
//    @Nullable
//    @Override
//    public Long size() {
//        return new MonitorCommand(MonitorMethod.create("size"), getCategoryConfig()).execute(getKey(), () -> ops.size(getKey()));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#range(org.springframework.data.domain.Range, org.springframework.data.redis.connection.RedisZSetCommands.Limit)
//     */
//    @Nullable
//    @Override
//    public List<MapRecord<K, HK, HV>> range(Range<String> range, RedisZSetCommands.Limit limit) {
//        return new MonitorCommand(MonitorMethod.create("range"), getCategoryConfig()).execute(getKey(), () -> ops.range(getKey(), range, limit));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#read(org.springframework.data.redis.connection.RedisStreamCommands.StreamReadOptions, org.springframework.data.redis.connection.RedisStreamCommands.ReadOffset)
//     */
//    @Nullable
//    @Override
//    public List<MapRecord<K, HK, HV>> read(StreamReadOptions readOptions, ReadOffset readOffset) {
//        return new MonitorCommand(MonitorMethod.create("read"), getCategoryConfig()).execute(getKey(), () -> ops.read(readOptions, StreamOffset.create(getKey(), readOffset)));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#read(org.springframework.data.redis.connection.RedisStreamCommands.Consumer, org.springframework.data.redis.connection.RedisStreamCommands.StreamReadOptions, org.springframework.data.redis.connection.RedisStreamCommands.ReadOffset)
//     */
//    @Nullable
//    @Override
//    public List<MapRecord<K, HK, HV>> read(Consumer consumer, StreamReadOptions readOptions, ReadOffset readOffset) {
//        return new MonitorCommand(MonitorMethod.create("read"), getCategoryConfig()).execute(getKey(), () -> ops.read(consumer, readOptions, StreamOffset.create(getKey(), readOffset)));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#reverseRange(org.springframework.data.domain.Range, org.springframework.data.redis.connection.RedisZSetCommands.Limit)
//     */
//    @Nullable
//    @Override
//    public List<MapRecord<K, HK, HV>> reverseRange(Range<String> range, RedisZSetCommands.Limit limit) {
//        return new MonitorCommand(MonitorMethod.create("reverseRange"), getCategoryConfig()).execute(getKey(), () -> ops.reverseRange(getKey(), range, limit));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundStreamOperations#trim(long)
//     */
//    @Nullable
//    @Override
//    public Long trim(long count) {
//        return new MonitorCommand(MonitorMethod.create("trim"), getCategoryConfig()).execute(getKey(), () -> ops.trim(getKey(), count));
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.redis.core.BoundKeyOperations#getType()
//     */
//    @Nullable
//    @Override
//    public DataType getType() {
//        return new MonitorCommand(MonitorMethod.create("getType"), getCategoryConfig()).execute(getKey(), () -> DataType.STREAM);
//    }
//}

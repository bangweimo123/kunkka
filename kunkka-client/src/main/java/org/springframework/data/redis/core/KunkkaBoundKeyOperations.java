package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import com.leshiguang.arch.kunkka.client.exception.KunkkaChangeDurationException;
import com.leshiguang.arch.kunkka.client.exception.KunkkaUnsupportTypeException;
import org.springframework.data.redis.connection.DataType;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-12 11:37
 * @Description
 */
public class KunkkaBoundKeyOperations<K extends StoreKey> extends AbstractMonitorOperations implements BoundKeyOperations<K> {
    private K key;
    private CategoryConfig categoryConfig;
    private final RedisOperations<K, ?> ops;

    KunkkaBoundKeyOperations(CategoryConfig categoryConfig, K key, RedisOperations<K, ?> operations) {
        this.categoryConfig = categoryConfig;
        if (!categoryConfig.getcType().equalsIgnoreCase(getType().toString())) {
            throw new KunkkaUnsupportTypeException();
        }
        this.key = key;
        this.ops = operations;

    }

    protected CategoryConfig getCategoryConfig() {
        return categoryConfig;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#getKey()
     */
    @Override
    public K getKey() {
        return key;
    }

    @Override
    public DataType getType() {
        return DataType.fromCode(categoryConfig.getcType());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#expire(long, java.util.concurrent.TimeUnit)
     */

    /**
     * 只提供给Kunkka内部调用,外部无法访问
     *
     * @param timeout
     * @param unit
     * @return
     */
    protected Boolean expireInner(long timeout, TimeUnit unit) {
        return ops.expire(key, timeout, unit);
    }

    @Override
    public Boolean expire(long timeout, TimeUnit unit) {
        if (!categoryConfig.getDurationChangeabled()) {
            throw new KunkkaChangeDurationException();
        }
        return new MonitorCommand(MonitorMethod.create("expire").setExpire(timeout, unit), categoryConfig).execute(key, () -> ops.expire(key, timeout, unit));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#expireAt(java.util.Date)
     */
    @Override
    public Boolean expireAt(Date date) {
        if (!categoryConfig.getDurationChangeabled()) {
            throw new KunkkaChangeDurationException();
        }
        return new MonitorCommand(MonitorMethod.create("expireAt").setExpire(date), categoryConfig).execute(key, () -> ops.expireAt(key, date));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#getExpire()
     */
    @Override
    public Long getExpire() {
        return new MonitorCommand(MonitorMethod.create("getExpire"), categoryConfig).execute(key, () -> ops.getExpire(key));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#persist()
     */
    @Override
    public Boolean persist() {
        return new MonitorCommand(MonitorMethod.create("persist"), categoryConfig).execute(key, () -> ops.persist(key));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#rename(java.lang.Object)
     */
    @Override
    public void rename(K newKey) {
        if (ops.hasKey(key)) {
            ops.rename(key, newKey);
        }
        key = newKey;
    }
}

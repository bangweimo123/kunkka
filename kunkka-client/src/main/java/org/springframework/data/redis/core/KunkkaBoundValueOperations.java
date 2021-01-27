package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import com.leshiguang.arch.kunkka.client.exception.KunkkaChangeDurationException;
import com.leshiguang.arch.kunkka.client.exception.KunkkaUnsupportMethodException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-21 13:58
 * @Description
 */
public class KunkkaBoundValueOperations<K extends StoreKey, V extends Serializable> extends KunkkaBoundKeyOperations<K> implements BoundValueOperations<K, V> {
    private final ValueOperations<K, V> ops;

    private final ValueOperations<K, V> hotOps;

    /**
     * Constructs a new {@link DefaultBoundValueOperations} instance.
     *
     * @param key
     * @param operations
     */
    KunkkaBoundValueOperations(CategoryConfig categoryConfig, K key, RedisOperations<K, V> operations, ValueOperations<K, V> hotOperations) {
        super(categoryConfig, key, operations);
        this.ops = operations.opsForValue();
        this.hotOps = hotOperations;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#get()
     */
    @Override
    public V get() {
        return new MonitorCommand(MonitorMethod.create("get"), getCategoryConfig()).execute(getKey(), () -> {
            if (getCategoryConfig().getHot()) {
                V data = hotOps.get(getKey());
                if (null == data) {
                    data = ops.get(getKey());
                    hotOps.set(getKey(), data);
                }
                return data;
            } else {
                return ops.get(getKey());
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#getAndSet(java.lang.Object)
     */
    @Override
    public V getAndSet(V value) {
        return new MonitorCommand(MonitorMethod.create("getAndSet").setExpireable(), getCategoryConfig()).execute(getKey(), () -> {
            if (getCategoryConfig().getHot()) {
                return hotOps.getAndSet(getKey(), value);
            } else {
                return ops.getAndSet(getKey(), value);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#increment()
     */
    @Override
    public Long increment() {
        return new MonitorCommand(MonitorMethod.create("increment"), getCategoryConfig()).execute(getKey(), () -> {
            Long data = ops.increment(getKey());
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), (V) data);
            }
            return data;
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#increment(long)
     */
    @Override
    public Long increment(long delta) {
        return new MonitorCommand(MonitorMethod.create("increment"), getCategoryConfig()).execute(getKey(), () -> {
            Long data = ops.increment(getKey(), delta);
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), (V) data);
            }
            return data;
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#increment(double)
     */
    @Override
    public Double increment(double delta) {
        return new MonitorCommand(MonitorMethod.create("increment"), getCategoryConfig()).execute(getKey(), () -> {
            Double data = ops.increment(getKey(), delta);
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), (V) data);
            }
            return data;
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#decrement()
     */
    @Override
    public Long decrement() {
        return new MonitorCommand(MonitorMethod.create("decrement"), getCategoryConfig()).execute(getKey(), () -> {
            Long data = ops.decrement(getKey());
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), (V) data);
            }
            return data;
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#decrement(double)
     */
    @Override
    public Long decrement(long delta) {
        return new MonitorCommand(MonitorMethod.create("decrement"), getCategoryConfig()).execute(getKey(), () -> {
            Long data = ops.decrement(getKey(), delta);
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), (V) data);
            }
            return data;
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#append(java.lang.String)
     */
    @Override
    public Integer append(String value) {
        return new MonitorCommand(MonitorMethod.create("append"), getCategoryConfig()).execute(getKey(), () -> ops.append(getKey(), value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#get(long, long)
     */
    @Override
    public String get(long start, long end) {
        return new MonitorCommand(MonitorMethod.create("get"), getCategoryConfig()).execute(getKey(), () -> ops.get(getKey(), start, end));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#set(java.lang.Object, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public void set(V value, long timeout, TimeUnit unit) {
        if (!getCategoryConfig().getDurationChangeabled()) {
            throw new KunkkaChangeDurationException();
        }
        new MonitorCommand(MonitorMethod.create("set").setExpire(timeout, unit), getCategoryConfig()).execute(getKey(), () -> {
            ops.set(getKey(), value, timeout, unit);
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), value);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#set(java.lang.Object)
     */
    @Override
    public void set(V value) {
        new MonitorCommand(MonitorMethod.create("set").setExpireable(), getCategoryConfig()).execute(getKey(), () -> {
            ops.set(getKey(), value);
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), value);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#setIfAbsent(java.lang.Object)
     */
    @Override
    public Boolean setIfAbsent(V value) {
        return new MonitorCommand(MonitorMethod.create("setIfAbsent").setExpireable(), getCategoryConfig()).execute(getKey(), () -> {
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), value);
            }
            return ops.setIfAbsent(getKey(), value);
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#setIfAbsent(java.lang.Object, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public Boolean setIfAbsent(V value, long timeout, TimeUnit unit) {
        if (!getCategoryConfig().getDurationChangeabled()) {
            throw new KunkkaChangeDurationException();
        }
        return new MonitorCommand(MonitorMethod.create("setIfAbsent").setExpireable(), getCategoryConfig()).execute(getKey(), () -> {
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), value);
            }
            return ops.setIfAbsent(getKey(), value, timeout, unit);
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#setIfPresent(java.lang.Object)
     */
    @Nullable
    @Override
    public Boolean setIfPresent(V value) {
        return new MonitorCommand(MonitorMethod.create("setIfPresent"), getCategoryConfig()).execute(getKey(), () -> {
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), value);
            }
            return ops.setIfPresent(getKey(), value);
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#setIfPresent(java.lang.Object, long, java.util.concurrent.TimeUnit)
     */
    @Nullable
    @Override
    public Boolean setIfPresent(V value, long timeout, TimeUnit unit) {
        if (!getCategoryConfig().getDurationChangeabled()) {
            throw new KunkkaChangeDurationException();
        }
        return new MonitorCommand(MonitorMethod.create("setIfPresent"), getCategoryConfig()).execute(getKey(), () -> {
            if (getCategoryConfig().getHot()) {
                hotOps.set(getKey(), value);
            }
            return ops.setIfPresent(getKey(), value, timeout, unit);
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#set(java.lang.Object, long)
     */
    @Override
    public void set(V value, long offset) {
        new MonitorCommand(MonitorMethod.create("set").setExpireable(), getCategoryConfig()).execute(getKey(), () ->
                ops.set(getKey(), value, offset)
        );
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#size()
     */
    @Override
    public Long size() {
        return new MonitorCommand(MonitorMethod.create("size"), getCategoryConfig()).execute(getKey(), () -> ops.size(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundValueOperations#getOperations()
     */
    @Override
    public RedisOperations<K, V> getOperations() {
        return ops.getOperations();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#getType()
     */
    @Override
    public DataType getType() {
        return DataType.STRING;
    }
}

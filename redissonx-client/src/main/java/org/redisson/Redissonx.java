package org.redisson;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfigManager;
import com.leshiguang.arch.redissonx.exception.StoreException;
import org.redisson.api.*;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-02-11 19:02
 * @Modify 修改redisson的key为Storekey
 */
public class Redissonx extends Redisson implements RedissonxClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(Redissonx.class);
    private StoreCategoryConfigManager storeCategoryConfigManager;

    public void setStoreCategoryConfigManager(StoreCategoryConfigManager storeCategoryConfigManager) {
        this.storeCategoryConfigManager = storeCategoryConfigManager;
    }

    protected Redissonx(Config config) {
        super(config);
    }

    public static RedissonxClient create(String clusterName, Config config) {
        Redissonx redissonx = new Redissonx(config);
        if (config.isReferenceEnabled()) {
            redissonx.enableRedissonReferenceSupport();
        }
        StoreCategoryConfigManager storeCategoryConfigManager = new StoreCategoryConfigManager(clusterName);
        redissonx.setStoreCategoryConfigManager(storeCategoryConfigManager);
        return redissonx;
    }

    protected StoreCategoryConfig parseCategoryConfig(StoreKey name) {
        final StoreCategoryConfig categoryConfig = storeCategoryConfigManager.getStoreCategoryConfig(name.getCategory());
        if (null == categoryConfig) {
            throw new StoreException("can't find categoryConfig form category:[" + name.getCategory() + "]");
        }
        return categoryConfig;
    }

    /**
     * 代理门面，所有请求都经过代理门面来操作
     *
     * @param <T>
     */
    static class RFacade<T extends RObject> {
        private T data;

        public RFacade(Redissonx redissonx, StoreKey storeKey, RI<T> ri) {
            this(redissonx, storeKey, redissonx.connectionManager.getCodec(), ri);
        }

        public RFacade(Redissonx redissonx, StoreKey storeKey, Codec codec, RI<T> ri) {
            StoreCategoryConfig categoryConfig = redissonx.parseCategoryConfig(storeKey);
            String finalName = categoryConfig.getFinalKey(storeKey);
            T result = ri.build(redissonx, finalName, codec);
            if (result instanceof RedissonExpirable) {
                RedissonExpirable expirable = (RedissonExpirable) result;
                boolean expireResult = expirable.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
                if (!expireResult) {
                    LOGGER.warn("set expire info error");
                }
            }
            //热key逻辑
            if (categoryConfig.getHot() && result instanceof RBucket) {
                result = (T) new RedissonxLocalBucket(codec, redissonx.connectionManager.getCommandExecutor(), finalName, categoryConfig.getHotHolder());
            }

            data = result;
        }

        public T get() {
            return data;
        }

        interface RI<T extends RObject> {
            T build(Redissonx redissonx, String _name, Codec _codec);
        }

    }

    @Override
    public <K, V> RStream<K, V> getStream(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonStream(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <K, V> RStream<K, V> getStream(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonStream(_codec, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public RBinaryStream getBinaryStream(StoreKey name) {
        return new RFacade<>(this, name, (RFacade.RI<RBinaryStream>) (redissonx, _name, _codec) -> new RedissonBinaryStream(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <V> RGeo<V> getGeo(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonGeo(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RGeo<V> getGeo(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonGeo(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RBucket<V> getBucket(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonBucket(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public RRateLimiter getRateLimiter(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonRateLimiter(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <V> RBucket<V> getBucket(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonBucket(_codec, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <V> RHyperLogLog<V> getHyperLogLog(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonHyperLogLog(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <V> RHyperLogLog<V> getHyperLogLog(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonHyperLogLog(_codec, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <V> RList<V> getList(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonList(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RList<V> getList(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonList(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <K, V> RListMultimap<K, V> getListMultimap(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonListMultimap(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <K, V> RListMultimap<K, V> getListMultimap(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonListMultimap(_codec, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <K, V> RMap<K, V> getMap(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonMap(redissonx.connectionManager.getCommandExecutor(), _name, redissonx, (MapOptions) null, (WriteBehindService) null)).get();
    }

    @Override
    public <K, V> RMap<K, V> getMap(StoreKey name, MapOptions<K, V> options) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonMap(redissonx.connectionManager.getCommandExecutor(), _name, redissonx, options, (WriteBehindService) null)).get();
    }

    @Override
    public <K, V> RSetMultimap<K, V> getSetMultimap(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonSetMultimap(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonSetMultimapCache(redissonx.evictionScheduler, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonSetMultimapCache(redissonx.evictionScheduler, _codec, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <K, V> RListMultimapCache<K, V> getListMultimapCache(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonListMultimapCache(redissonx.evictionScheduler, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <K, V> RListMultimapCache<K, V> getListMultimapCache(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonListMultimapCache(redissonx.evictionScheduler, _codec, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <K, V> RSetMultimap<K, V> getSetMultimap(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonSetMultimap(_codec, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <V> RSetCache<V> getSetCache(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonSetCache(redissonx.evictionScheduler, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RSetCache<V> getSetCache(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonSetCache(_codec, redissonx.evictionScheduler, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <K, V> RMapCache<K, V> getMapCache(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonMapCache(redissonx.evictionScheduler, redissonx.connectionManager.getCommandExecutor(), _name, redissonx, (MapOptions) null, (WriteBehindService) null)).
                get();
    }

    @Override
    public <K, V> RMapCache<K, V> getMapCache(StoreKey name, MapOptions<K, V> options) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonMapCache(redissonx.evictionScheduler, redissonx.connectionManager.getCommandExecutor(), _name, redissonx, options, (WriteBehindService) null)).
                get();
    }

    @Override
    public <K, V> RMapCache<K, V> getMapCache(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonMapCache(_codec, redissonx.evictionScheduler, redissonx.connectionManager.getCommandExecutor(), _name, redissonx, (MapOptions) null, (WriteBehindService) null)).
                get();
    }

    @Override
    public <K, V> RMapCache<K, V> getMapCache(StoreKey name, Codec codec, MapOptions<K, V> options) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonMapCache(_codec, redissonx.evictionScheduler, redissonx.connectionManager.getCommandExecutor(), _name, redissonx, options, (WriteBehindService) null)).
                get();
    }

    @Override
    public <K, V> RMap<K, V> getMap(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonMap(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx, (MapOptions) null, (WriteBehindService) null)).
                get();
    }

    @Override
    public <K, V> RMap<K, V> getMap(StoreKey name, Codec codec, MapOptions<K, V> options) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonMap(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx, options, (WriteBehindService) null)).
                get();
    }

    @Override
    public <V> RSet<V> getSet(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonSet(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RSet<V> getSet(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonSet(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RSortedSet<V> getSortedSet(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonSortedSet(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RSortedSet<V> getSortedSet(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonSortedSet(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RScoredSortedSet<V> getScoredSortedSet(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonScoredSortedSet(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RScoredSortedSet<V> getScoredSortedSet(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonScoredSortedSet(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public RLexSortedSet getLexSortedSet(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonLexSortedSet(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }


    @Override
    public <V> RQueue<V> getQueue(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonQueue(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RQueue<V> getQueue(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonQueue(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RBlockingQueue<V> getBlockingQueue(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonBlockingQueue(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RBlockingQueue<V> getBlockingQueue(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonBlockingQueue(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonBoundedBlockingQueue(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonBoundedBlockingQueue(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RDeque<V> getDeque(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonDeque(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RDeque<V> getDeque(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonDeque(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RBlockingDeque<V> getBlockingDeque(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonBlockingDeque(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RBlockingDeque<V> getBlockingDeque(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonBlockingDeque(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public RAtomicLong getAtomicLong(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonAtomicLong(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public RLongAdder getLongAdder(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonLongAdder(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public RDoubleAdder getDoubleAdder(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonDoubleAdder(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public RAtomicDouble getAtomicDouble(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonAtomicDouble(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }


    @Override
    public RBitSet getBitSet(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonBitSet(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public RSemaphore getSemaphore(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonSemaphore(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public RPermitExpirableSemaphore getPermitExpirableSemaphore(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonPermitExpirableSemaphore(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <V> RBloomFilter<V> getBloomFilter(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonBloomFilter(redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }

    @Override
    public <V> RBloomFilter<V> getBloomFilter(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonBloomFilter(_codec, redissonx.connectionManager.getCommandExecutor(), _name)).get();
    }


    @Override
    public <V> RPriorityQueue<V> getPriorityQueue(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonPriorityQueue(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RPriorityQueue<V> getPriorityQueue(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonPriorityQueue(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonPriorityBlockingQueue(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonPriorityBlockingQueue(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonPriorityBlockingDeque(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonPriorityBlockingDeque(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RPriorityDeque<V> getPriorityDeque(StoreKey name) {
        return new RFacade<>(this, name, (redissonx, _name, _codec) -> new RedissonPriorityDeque(redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }

    @Override
    public <V> RPriorityDeque<V> getPriorityDeque(StoreKey name, Codec codec) {
        return new RFacade<>(this, name, codec, (redissonx, _name, _codec) -> new RedissonPriorityDeque(_codec, redissonx.connectionManager.getCommandExecutor(), _name, redissonx)).get();
    }
}

package org.redisson;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfigManager;
import com.leshiguang.arch.redissonx.exception.StoreException;
import org.redisson.api.*;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-02-11 19:02
 * @Modify 修改redisson的key为Storekey
 */
public class Redissonx extends Redisson implements RedissonxClient {
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

    @Override
    public <K, V> RStream<K, V> getStream(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RedissonStream result = new RedissonStream(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RStream<K, V> getStream(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RedissonStream result = new RedissonStream(codec, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RBinaryStream getBinaryStream(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBinaryStream result = new RedissonBinaryStream(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RGeo<V> getGeo(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RedissonGeo result = new RedissonGeo(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RGeo<V> getGeo(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RedissonGeo result = new RedissonGeo(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBucket<V> getBucket(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBucket<V> result = new RedissonBucket(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RRateLimiter getRateLimiter(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RRateLimiter result = new RedissonRateLimiter(this.connectionManager.getCommandExecutor(), finalName);
        return result;
    }

    @Override
    public <V> RBucket<V> getBucket(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBucket<V> result = new RedissonBucket(codec, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RBuckets getBuckets() {
        return new RedissonBuckets(this.connectionManager.getCommandExecutor());
    }

    @Override
    public RBuckets getBuckets(Codec codec) {
        return new RedissonBuckets(codec, this.connectionManager.getCommandExecutor());
    }

    @Override
    public <V> RHyperLogLog<V> getHyperLogLog(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RHyperLogLog<V> result = new RedissonHyperLogLog(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RHyperLogLog<V> getHyperLogLog(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RHyperLogLog<V> result = new RedissonHyperLogLog(codec, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RList<V> getList(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RList<V> result = new RedissonList(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RList<V> getList(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RList<V> result = new RedissonList(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RListMultimap<K, V> getListMultimap(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RListMultimap<K, V> result = new RedissonListMultimap(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RListMultimap<K, V> getListMultimap(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RListMultimap<K, V> result = new RedissonListMultimap(codec, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RLocalCachedMap<K, V> getLocalCachedMap(StoreKey name, LocalCachedMapOptions<K, V> options) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RLocalCachedMap<K, V> result = new RedissonLocalCachedMap(this.connectionManager.getCommandExecutor(), finalName, options, this.evictionScheduler, this, this.writeBehindService);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RLocalCachedMap<K, V> getLocalCachedMap(StoreKey name, Codec codec, LocalCachedMapOptions<K, V> options) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RLocalCachedMap<K, V> result = new RedissonLocalCachedMap(codec, this.connectionManager.getCommandExecutor(), finalName, options, this.evictionScheduler, this, this.writeBehindService);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RMap<K, V> getMap(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RMap<K, V> result = new RedissonMap(this.connectionManager.getCommandExecutor(), finalName, this, (MapOptions) null, (WriteBehindService) null);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RMap<K, V> getMap(StoreKey name, MapOptions<K, V> options) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RMap<K, V> result = new RedissonMap(this.connectionManager.getCommandExecutor(), finalName, this, options, this.writeBehindService);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RSetMultimap<K, V> getSetMultimap(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSetMultimap<K, V> result = new RedissonSetMultimap(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSetMultimapCache<K, V> result = new RedissonSetMultimapCache(this.evictionScheduler, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSetMultimapCache<K, V> result = new RedissonSetMultimapCache(this.evictionScheduler, codec, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RListMultimapCache<K, V> getListMultimapCache(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RListMultimapCache<K, V> result = new RedissonListMultimapCache(this.evictionScheduler, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RListMultimapCache<K, V> getListMultimapCache(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RListMultimapCache<K, V> result = new RedissonListMultimapCache(this.evictionScheduler, codec, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RSetMultimap<K, V> getSetMultimap(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSetMultimap<K, V> result = new RedissonSetMultimap(codec, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RSetCache<V> getSetCache(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSetCache<V> result = new RedissonSetCache(this.evictionScheduler, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RSetCache<V> getSetCache(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSetCache<V> result = new RedissonSetCache(codec, this.evictionScheduler, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RMapCache<K, V> getMapCache(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RMapCache<K, V> result = new RedissonMapCache(this.evictionScheduler, this.connectionManager.getCommandExecutor(), finalName, this, (MapOptions) null, (WriteBehindService) null);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RMapCache<K, V> getMapCache(StoreKey name, MapOptions<K, V> options) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RMapCache<K, V> result = new RedissonMapCache(this.evictionScheduler, this.connectionManager.getCommandExecutor(), finalName, this, options, this.writeBehindService);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RMapCache<K, V> getMapCache(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RMapCache<K, V> result = new RedissonMapCache(codec, this.evictionScheduler, this.connectionManager.getCommandExecutor(), finalName, this, (MapOptions) null, (WriteBehindService) null);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RMapCache<K, V> getMapCache(StoreKey name, Codec codec, MapOptions<K, V> options) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RMapCache<K, V> result = new RedissonMapCache(codec, this.evictionScheduler, this.connectionManager.getCommandExecutor(), finalName, this, options, this.writeBehindService);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RMap<K, V> getMap(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RMap<K, V> result = new RedissonMap(codec, this.connectionManager.getCommandExecutor(), finalName, this, (MapOptions) null, (WriteBehindService) null);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <K, V> RMap<K, V> getMap(StoreKey name, Codec codec, MapOptions<K, V> options) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RMap<K, V> result = new RedissonMap(codec, this.connectionManager.getCommandExecutor(), finalName, this, options, this.writeBehindService);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RSet<V> getSet(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSet<V> result = new RedissonSet(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RSet<V> getSet(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSet<V> result = new RedissonSet(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RSortedSet<V> getSortedSet(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSortedSet<V> result = new RedissonSortedSet(this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RSortedSet<V> getSortedSet(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSortedSet<V> result = new RedissonSortedSet(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RScoredSortedSet<V> getScoredSortedSet(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RScoredSortedSet<V> result = new RedissonScoredSortedSet(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RScoredSortedSet<V> getScoredSortedSet(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RScoredSortedSet<V> result = new RedissonScoredSortedSet(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RLexSortedSet getLexSortedSet(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RLexSortedSet result = new RedissonLexSortedSet(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }


    @Override
    public <V> RQueue<V> getQueue(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RQueue<V> result = new RedissonQueue(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RQueue<V> getQueue(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RQueue<V> result = new RedissonQueue(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBlockingQueue<V> getBlockingQueue(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBlockingQueue<V> result = new RedissonBlockingQueue(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBlockingQueue<V> getBlockingQueue(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBlockingQueue<V> result = new RedissonBlockingQueue(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBoundedBlockingQueue<V> result = new RedissonBoundedBlockingQueue(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBoundedBlockingQueue<V> result = new RedissonBoundedBlockingQueue(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RDeque<V> getDeque(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RDeque<V> result = new RedissonDeque(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RDeque<V> getDeque(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RDeque<V> result = new RedissonDeque(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBlockingDeque<V> getBlockingDeque(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBlockingDeque<V> result = new RedissonBlockingDeque(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBlockingDeque<V> getBlockingDeque(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBlockingDeque<V> result = new RedissonBlockingDeque(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RAtomicLong getAtomicLong(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RAtomicLong result = new RedissonAtomicLong(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RLongAdder getLongAdder(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RLongAdder result = new RedissonLongAdder(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RDoubleAdder getDoubleAdder(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RDoubleAdder result = new RedissonDoubleAdder(this.connectionManager.getCommandExecutor(), finalName, this);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RAtomicDouble getAtomicDouble(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RAtomicDouble result = new RedissonAtomicDouble(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }


    @Override
    public RBitSet getBitSet(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBitSet result = new RedissonBitSet(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RSemaphore getSemaphore(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RSemaphore result = new RedissonSemaphore(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public RPermitExpirableSemaphore getPermitExpirableSemaphore(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPermitExpirableSemaphore result = new RedissonPermitExpirableSemaphore(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBloomFilter<V> getBloomFilter(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBloomFilter<V> result = new RedissonBloomFilter(this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <V> RBloomFilter<V> getBloomFilter(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RBloomFilter<V> result = new RedissonBloomFilter(codec, this.connectionManager.getCommandExecutor(), finalName);
        result.expire(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        return result;
    }


    @Override
    public <V> RPriorityQueue<V> getPriorityQueue(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPriorityQueue<V> result = new RedissonPriorityQueue(this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RPriorityQueue<V> getPriorityQueue(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPriorityQueue<V> result = new RedissonPriorityQueue(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPriorityBlockingQueue<V> result = new RedissonPriorityBlockingQueue(this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPriorityBlockingQueue<V> result = new RedissonPriorityBlockingQueue(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPriorityBlockingDeque<V> result = new RedissonPriorityBlockingDeque(this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPriorityBlockingDeque<V> result = new RedissonPriorityBlockingDeque(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RPriorityDeque<V> getPriorityDeque(StoreKey name) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPriorityDeque<V> result = new RedissonPriorityDeque(this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }

    @Override
    public <V> RPriorityDeque<V> getPriorityDeque(StoreKey name, Codec codec) {
        StoreCategoryConfig categoryConfig = parseCategoryConfig(name);
        String finalName = categoryConfig.getFinalKey(name);
        RPriorityDeque<V> result = new RedissonPriorityDeque(codec, this.connectionManager.getCommandExecutor(), finalName, this);
        return result;
    }
}

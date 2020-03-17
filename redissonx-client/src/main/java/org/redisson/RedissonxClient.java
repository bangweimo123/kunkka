package org.redisson;

import com.leshiguang.arch.redissonx.client.StoreKey;
import org.redisson.api.*;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-02-11 18:38
 * @Modify
 */
public interface RedissonxClient extends RedissonClient {
    /**
     * Returns stream instance by <code>name</code>
     * <p>
     * Requires <b>Redis 5.0.0 and higher.</b>
     *
     * @param <K>  type of key
     * @param <V>  type of value
     * @param name of stream
     * @return RStream object
     */
    <K, V> RStream<K, V> getStream(StoreKey name);

    /**
     * Returns stream instance by <code>name</code>
     * using provided <code>codec</code> for entries.
     * <p>
     * Requires <b>Redis 5.0.0 and higher.</b>
     *
     * @param <K>   type of key
     * @param <V>   type of value
     * @param name  - name of stream
     * @param codec - codec for entry
     * @return RStream object
     */
    <K, V> RStream<K, V> getStream(StoreKey name, Codec codec);

    /**
     * Returns rate limiter instance by <code>name</code>
     *
     * @param name of rate limiter
     * @return RateLimiter object
     */
    RRateLimiter getRateLimiter(StoreKey name);

    /**
     * Returns binary stream holder instance by <code>name</code>
     *
     * @param name of binary stream
     * @return BinaryStream object
     */
    RBinaryStream getBinaryStream(StoreKey name);

    /**
     * Returns geospatial items holder instance by <code>name</code>.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return Geo object
     */
    <V> RGeo<V> getGeo(StoreKey name);

    /**
     * Returns geospatial items holder instance by <code>name</code>
     * using provided codec for geospatial members.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for value
     * @return Geo object
     */
    <V> RGeo<V> getGeo(StoreKey name, Codec codec);

    /**
     * Returns set-based cache instance by <code>name</code>.
     * Supports value eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSet(String, Codec)}.</p>
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return SetCache object
     */
    <V> RSetCache<V> getSetCache(StoreKey name);

    /**
     * Returns set-based cache instance by <code>name</code>.
     * Supports value eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSet(String, Codec)}.</p>
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return SetCache object
     */
    <V> RSetCache<V> getSetCache(StoreKey name, Codec codec);

    /**
     * Returns map-based cache instance by <code>name</code>
     * using provided <code>codec</code> for both cache keys and values.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String, Codec)}.
     *
     * @param <K>   type of key
     * @param <V>   type of value
     * @param name  - object name
     * @param codec - codec for keys and values
     * @return MapCache object
     */
    <K, V> RMapCache<K, V> getMapCache(StoreKey name, Codec codec);

    /**
     * Returns map-based cache instance by <code>name</code>
     * using provided <code>codec</code> for both cache keys and values.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String, Codec)}.
     *
     * @param <K>     type of key
     * @param <V>     type of value
     * @param name    - object name
     * @param codec   - codec for keys and values
     * @param options - map options
     * @return MapCache object
     */
    <K, V> RMapCache<K, V> getMapCache(StoreKey name, Codec codec, MapOptions<K, V> options);

    /**
     * Returns map-based cache instance by name.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String)}.</p>
     *
     * @param <K>  type of key
     * @param <V>  type of value
     * @param name - name of object
     * @return MapCache object
     */
    <K, V> RMapCache<K, V> getMapCache(StoreKey name);

    /**
     * Returns map-based cache instance by name.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String)}.</p>
     *
     * @param <K>     type of key
     * @param <V>     type of value
     * @param name    - name of object
     * @param options - map options
     * @return MapCache object
     */
    <K, V> RMapCache<K, V> getMapCache(StoreKey name, MapOptions<K, V> options);

    /**
     * Returns object holder instance by name.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return Bucket object
     */
    <V> RBucket<V> getBucket(StoreKey name);

    /**
     * Returns object holder instance by name
     * using provided codec for object.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return Bucket object
     */
    <V> RBucket<V> getBucket(StoreKey name, Codec codec);

    /**
     * Returns interface for mass operations with Bucket objects.
     *
     * @return Buckets
     */
    RBuckets getBuckets();

    /**
     * Returns interface for mass operations with Bucket objects
     * using provided codec for object.
     *
     * @param codec - codec for bucket objects
     * @return Buckets
     */
    RBuckets getBuckets(Codec codec);

    /**
     * Returns HyperLogLog instance by name.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return HyperLogLog object
     */
    <V> RHyperLogLog<V> getHyperLogLog(StoreKey name);

    /**
     * Returns HyperLogLog instance by name
     * using provided codec for hll objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return HyperLogLog object
     */
    <V> RHyperLogLog<V> getHyperLogLog(StoreKey name, Codec codec);

    /**
     * Returns list instance by name.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return List object
     */
    <V> RList<V> getList(StoreKey name);

    /**
     * Returns list instance by name
     * using provided codec for list objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return List object
     */
    <V> RList<V> getList(StoreKey name, Codec codec);

    /**
     * Returns List based Multimap instance by name.
     *
     * @param <K>  type of key
     * @param <V>  type of value
     * @param name - name of object
     * @return ListMultimap object
     */
    <K, V> RListMultimap<K, V> getListMultimap(StoreKey name);

    /**
     * Returns List based Multimap instance by name
     * using provided codec for both map keys and values.
     *
     * @param <K>   type of key
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return ListMultimap object
     */
    <K, V> RListMultimap<K, V> getListMultimap(StoreKey name, Codec codec);

    /**
     * Returns List based Multimap instance by name.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String)}.</p>
     *
     * @param <K>  type of key
     * @param <V>  type of value
     * @param name - name of object
     * @return ListMultimapCache object
     */
    <K, V> RListMultimapCache<K, V> getListMultimapCache(StoreKey name);

    /**
     * Returns List based Multimap instance by name
     * using provided codec for both map keys and values.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String, Codec)}.</p>
     *
     * @param <K>   type of key
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return ListMultimapCache object
     */
    <K, V> RListMultimapCache<K, V> getListMultimapCache(StoreKey name, Codec codec);

    /**
     * Returns local cached map instance by name.
     * Configured by parameters of options-object.
     *
     * @param <K>     type of key
     * @param <V>     type of value
     * @param name    - name of object
     * @param options - local map options
     * @return LocalCachedMap object
     */
    <K, V> RLocalCachedMap<K, V> getLocalCachedMap(StoreKey name, LocalCachedMapOptions<K, V> options);

    /**
     * Returns local cached map instance by name
     * using provided codec. Configured by parameters of options-object.
     *
     * @param <K>     type of key
     * @param <V>     type of value
     * @param name    - name of object
     * @param codec   - codec for keys and values
     * @param options - local map options
     * @return LocalCachedMap object
     */
    <K, V> RLocalCachedMap<K, V> getLocalCachedMap(StoreKey name, Codec codec, LocalCachedMapOptions<K, V> options);

    /**
     * Returns map instance by name.
     *
     * @param <K>  type of key
     * @param <V>  type of value
     * @param name - name of object
     * @return Map object
     */
    <K, V> RMap<K, V> getMap(StoreKey name);

    /**
     * Returns map instance by name.
     *
     * @param <K>     type of key
     * @param <V>     type of value
     * @param name    - name of object
     * @param options - map options
     * @return Map object
     */
    <K, V> RMap<K, V> getMap(StoreKey name, MapOptions<K, V> options);

    /**
     * Returns map instance by name
     * using provided codec for both map keys and values.
     *
     * @param <K>   type of key
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return Map object
     */
    <K, V> RMap<K, V> getMap(StoreKey name, Codec codec);

    /**
     * Returns map instance by name
     * using provided codec for both map keys and values.
     *
     * @param <K>     type of key
     * @param <V>     type of value
     * @param name    - name of object
     * @param codec   - codec for keys and values
     * @param options - map options
     * @return Map object
     */
    <K, V> RMap<K, V> getMap(StoreKey name, Codec codec, MapOptions<K, V> options);

    /**
     * Returns Set based Multimap instance by name.
     *
     * @param <K>  type of key
     * @param <V>  type of value
     * @param name - name of object
     * @return SetMultimap object
     */
    <K, V> RSetMultimap<K, V> getSetMultimap(StoreKey name);

    /**
     * Returns Set based Multimap instance by name
     * using provided codec for both map keys and values.
     *
     * @param <K>   type of key
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return SetMultimap object
     */
    <K, V> RSetMultimap<K, V> getSetMultimap(StoreKey name, Codec codec);

    /**
     * Returns Set based Multimap instance by name.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String)}.</p>
     *
     * @param <K>  type of key
     * @param <V>  type of value
     * @param name - name of object
     * @return SetMultimapCache object
     */
    <K, V> RSetMultimapCache<K, V> getSetMultimapCache(StoreKey name);

    /**
     * Returns Set based Multimap instance by name
     * using provided codec for both map keys and values.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String, Codec)}.</p>
     *
     * @param <K>   type of key
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return SetMultimapCache object
     */
    <K, V> RSetMultimapCache<K, V> getSetMultimapCache(StoreKey name, Codec codec);

    /**
     * Returns semaphore instance by name
     *
     * @param name - name of object
     * @return Semaphore object
     */
    RSemaphore getSemaphore(StoreKey name);

    /**
     * Returns semaphore instance by name.
     * Supports lease time parameter for each acquired permit.
     *
     * @param name - name of object
     * @return PermitExpirableSemaphore object
     */
    RPermitExpirableSemaphore getPermitExpirableSemaphore(StoreKey name);

    /**
     * Returns set instance by name.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return Set object
     */
    <V> RSet<V> getSet(StoreKey name);

    /**
     * Returns set instance by name
     * using provided codec for set objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return Set object
     */
    <V> RSet<V> getSet(StoreKey name, Codec codec);

    /**
     * Returns sorted set instance by name.
     * This sorted set uses comparator to sort objects.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return SortedSet object
     */
    <V> RSortedSet<V> getSortedSet(StoreKey name);

    /**
     * Returns sorted set instance by name
     * using provided codec for sorted set objects.
     * This sorted set sorts objects using comparator.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return SortedSet object
     */
    <V> RSortedSet<V> getSortedSet(StoreKey name, Codec codec);

    /**
     * Returns Redis Sorted Set instance by name.
     * This sorted set sorts objects by object score.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return ScoredSortedSet object
     */
    <V> RScoredSortedSet<V> getScoredSortedSet(StoreKey name);

    /**
     * Returns Redis Sorted Set instance by name
     * using provided codec for sorted set objects.
     * This sorted set sorts objects by object score.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return ScoredSortedSet object
     */
    <V> RScoredSortedSet<V> getScoredSortedSet(StoreKey name, Codec codec);

    /**
     * Returns String based Redis Sorted Set instance by name
     * All elements are inserted with the same score during addition,
     * in order to force lexicographical ordering
     *
     * @param name - name of object
     * @return LexSortedSet object
     */
    RLexSortedSet getLexSortedSet(StoreKey name);

    /**
     * Returns unbounded queue instance by name.
     *
     * @param <V>  type of value
     * @param name of object
     * @return queue object
     */
    <V> RQueue<V> getQueue(StoreKey name);

    /**
     * Returns unbounded delayed queue instance by name.
     * <p>
     * Could be attached to destination queue only.
     * All elements are inserted with transfer delay to destination queue.
     *
     * @param <V>              type of value
     * @param destinationQueue - destination queue
     * @return Delayed queue object
     */
    <V> RDelayedQueue<V> getDelayedQueue(RQueue<V> destinationQueue);

    /**
     * Returns unbounded queue instance by name
     * using provided codec for queue objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    <V> RQueue<V> getQueue(StoreKey name, Codec codec);

    /**
     * Returns priority unbounded queue instance by name.
     * It uses comparator to sort objects.
     *
     * @param <V>  type of value
     * @param name of object
     * @return Queue object
     */
    <V> RPriorityQueue<V> getPriorityQueue(StoreKey name);

    /**
     * Returns priority unbounded queue instance by name
     * using provided codec for queue objects.
     * It uses comparator to sort objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    <V> RPriorityQueue<V> getPriorityQueue(StoreKey name, Codec codec);

    /**
     * Returns unbounded priority blocking queue instance by name.
     * It uses comparator to sort objects.
     *
     * @param <V>  type of value
     * @param name of object
     * @return Queue object
     */
    <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(StoreKey name);

    /**
     * Returns unbounded priority blocking queue instance by name
     * using provided codec for queue objects.
     * It uses comparator to sort objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(StoreKey name, Codec codec);

    /**
     * Returns unbounded priority blocking deque instance by name.
     * It uses comparator to sort objects.
     *
     * @param <V>  type of value
     * @param name of object
     * @return Queue object
     */
    <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(StoreKey name);

    /**
     * Returns unbounded priority blocking deque instance by name
     * using provided codec for queue objects.
     * It uses comparator to sort objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(StoreKey name, Codec codec);

    /**
     * Returns priority unbounded deque instance by name.
     * It uses comparator to sort objects.
     *
     * @param <V>  type of value
     * @param name of object
     * @return Queue object
     */
    <V> RPriorityDeque<V> getPriorityDeque(StoreKey name);

    /**
     * Returns priority unbounded deque instance by name
     * using provided codec for queue objects.
     * It uses comparator to sort objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    <V> RPriorityDeque<V> getPriorityDeque(StoreKey name, Codec codec);

    /**
     * Returns unbounded blocking queue instance by name.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return BlockingQueue object
     */
    <V> RBlockingQueue<V> getBlockingQueue(StoreKey name);

    /**
     * Returns unbounded blocking queue instance by name
     * using provided codec for queue objects.
     *
     * @param <V>   type of value
     * @param name  - name of queue
     * @param codec - queue objects codec
     * @return BlockingQueue object
     */
    <V> RBlockingQueue<V> getBlockingQueue(StoreKey name, Codec codec);

    /**
     * Returns bounded blocking queue instance by name.
     *
     * @param <V>  type of value
     * @param name of queue
     * @return BoundedBlockingQueue object
     */
    <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(StoreKey name);

    /**
     * Returns bounded blocking queue instance by name
     * using provided codec for queue objects.
     *
     * @param <V>   type of value
     * @param name  - name of queue
     * @param codec - codec for values
     * @return BoundedBlockingQueue object
     */
    <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(StoreKey name, Codec codec);

    /**
     * Returns unbounded deque instance by name.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return Deque object
     */
    <V> RDeque<V> getDeque(StoreKey name);

    /**
     * Returns unbounded deque instance by name
     * using provided codec for deque objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return Deque object
     */
    <V> RDeque<V> getDeque(StoreKey name, Codec codec);

    /**
     * Returns unbounded blocking deque instance by name.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return BlockingDeque object
     */
    <V> RBlockingDeque<V> getBlockingDeque(StoreKey name);

    /**
     * Returns unbounded blocking deque instance by name
     * using provided codec for deque objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - deque objects codec
     * @return BlockingDeque object
     */
    <V> RBlockingDeque<V> getBlockingDeque(StoreKey name, Codec codec);

    /**
     * Returns atomicLong instance by name.
     *
     * @param name - name of object
     * @return AtomicLong object
     */
    RAtomicLong getAtomicLong(StoreKey name);

    /**
     * Returns atomicDouble instance by name.
     *
     * @param name - name of object
     * @return AtomicDouble object
     */
    RAtomicDouble getAtomicDouble(StoreKey name);

    /**
     * Returns LongAdder instances by name.
     *
     * @param name - name of object
     * @return LongAdder object
     */
    RLongAdder getLongAdder(StoreKey name);

    /**
     * Returns DoubleAdder instances by name.
     *
     * @param name - name of object
     * @return LongAdder object
     */
    RDoubleAdder getDoubleAdder(StoreKey name);

    /**
     * Returns bitSet instance by name.
     *
     * @param name - name of object
     * @return BitSet object
     */
    RBitSet getBitSet(StoreKey name);

    /**
     * Returns bloom filter instance by name.
     *
     * @param <V>  type of value
     * @param name - name of object
     * @return BloomFilter object
     */
    <V> RBloomFilter<V> getBloomFilter(StoreKey name);

    /**
     * Returns bloom filter instance by name
     * using provided codec for objects.
     *
     * @param <V>   type of value
     * @param name  - name of object
     * @param codec - codec for values
     * @return BloomFilter object
     */
    <V> RBloomFilter<V> getBloomFilter(StoreKey name, Codec codec);

    /**
     * Returns script operations object
     *
     * @return Script object
     */
    RScript getScript();

    /**
     * Returns script operations object using provided codec.
     *
     * @param codec - codec for params and result
     * @return Script object
     */
    RScript getScript(Codec codec);

    /**
     * Creates transaction with <b>READ_COMMITTED</b> isolation level.
     *
     * @param options - transaction configuration
     * @return Transaction object
     */
    RTransaction createTransaction(TransactionOptions options);

    /**
     * Creates batch object which could be executed later
     * with collected group of commands in pipeline mode.
     * <p>
     * See <a href="http://redis.io/topics/pipelining">http://redis.io/topics/pipelining</a>
     *
     * @param options - batch configuration
     * @return Batch object
     */
    RBatch createBatch(BatchOptions options);

}

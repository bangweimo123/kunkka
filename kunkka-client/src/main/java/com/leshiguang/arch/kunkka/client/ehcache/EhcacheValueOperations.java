package com.leshiguang.arch.kunkka.client.ehcache;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.exception.KunkkaUnValidKeyException;
import com.leshiguang.arch.kunkka.client.exception.KunkkaUnsupportMethodException;
import com.leshiguang.arch.kunkka.client.lifecycle.Lifecycle;
import com.leshiguang.arch.kunkka.client.serialize.StoreKeyEhcacheSerializer;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.management.ManagementService;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.ValueOperations;

import javax.management.MBeanServer;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo
 * @Date 2021-01-12 16:51
 * @Description
 */
public class EhcacheValueOperations<K extends StoreKey, V> implements ValueOperations<K, V>, Lifecycle {
    /**
     * Default ehcache configuration file
     */
    private static final String EHCACHE_FILE_URL = "/ehcache.xml";

    private static final String CUSTOM_EHCACHE_FILE_URL = "/config/ehcache-cust.xml";
    private String xmlFile = EHCACHE_FILE_URL;
    private static CacheManager manager;

    private BlockingCache defaultCache;

    private static final String TEMPLATE_CACHE_NAME = "templateCache";
    private StoreKeyEhcacheSerializer serializer;

    public void clear() {
        defaultCache.removeAll();
        for (String cacheName : manager.getCacheNames()) {
            manager.getCache(cacheName).removeAll();
        }
    }

    public EhcacheValueOperations(StoreKeyEhcacheSerializer serializer) {
        this.serializer = serializer;
    }

    public CacheManager buildEhcacheManager() {
        if (getClass().getResource(CUSTOM_EHCACHE_FILE_URL) != null) {
            xmlFile = CUSTOM_EHCACHE_FILE_URL;
        }
        URL url = getClass().getResource(xmlFile);
        return CacheManager.newInstance(url);
    }

    @Override
    public void start() {
        //锁整个类初始化manager
        if (manager == null) {
            synchronized (EhcacheValueOperations.class) {
                manager = buildEhcacheManager();
                if (null == manager) {
                    manager = CacheManager.create();
                }
                MBeanServer server = ManagementFactory.getPlatformMBeanServer();
                ManagementService.registerMBeans(manager, server, true, true, true, true);
            }
        }
        //锁当前对象回去templateCache
        Ehcache templateCache = manager.getEhcache(TEMPLATE_CACHE_NAME);
        if (templateCache == null) {
            synchronized (this) {
                if (templateCache == null) {
                    // 用户自定义的可能没有 templateCache，这里手动new一个templateCache的配置，参考/ehcache.xml配置
                    templateCache = new Cache(new CacheConfiguration()
                            .name(TEMPLATE_CACHE_NAME)
                            .maxEntriesLocalHeap(50000)
                            .maxElementsOnDisk(1000000)
                            .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
                            .overflowToDisk(true)
                            .eternal(false)
                            .timeToLiveSeconds(300)
                            .timeToIdleSeconds(0)
                            .diskPersistent(false)
                            .diskExpiryThreadIntervalSeconds(120));
                    manager.addCache(templateCache);
                }
            }
        }
        //锁当前对象获取defaultCache
        if (defaultCache == null) {
            synchronized (this) {
                if (defaultCache == null) {
                    defaultCache = new LooseBlockingCache(templateCache);
                }
            }
        }
    }

    @Override
    public void stop() {
        this.clear();
        manager.shutdown();
    }

    private Ehcache findCache(String category) {
        if (null == category) {
            return defaultCache;
        }
        Ehcache ehcache = manager.getEhcache(category);
        if (null == ehcache) {
            return defaultCache;
        }
        return ehcache;
    }

    Serializable rawKey(StoreKey key) {
        return serializer.serialize(key);
    }


    @Override
    public void set(K key, V value) {
        Ehcache cache = findCache(key.getCategory());
        Serializable rawKey = rawKey(key);
        Element element = new Element(rawKey, value);
        cache.putQuiet(element);
    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit unit) {
        Ehcache cache = findCache(key.getCategory());
        Serializable rawKey = rawKey(key);
        Element element = new Element(rawKey, value);
        element.setTimeToLive(Long.valueOf(TimeoutUtils.toSeconds(timeout, unit)).intValue());
        cache.putQuiet(element);
    }

    @Override
    public Boolean setIfAbsent(K key, V value) {
        Ehcache cache = findCache(key.getCategory());
        Serializable rawKey = rawKey(key);
        Element element = new Element(rawKey, value);
        Element putEle = cache.putIfAbsent(element);
        return null != putEle;
    }

    @Override
    public Boolean setIfAbsent(K key, V value, long timeout, TimeUnit unit) {
        Ehcache cache = findCache(key.getCategory());
        Serializable rawKey = rawKey(key);
        Element element = new Element(rawKey, value);
        element.setTimeToLive(Long.valueOf(TimeoutUtils.toSeconds(timeout, unit)).intValue());
        Element putEle = cache.putIfAbsent(element);
        return null != putEle;
    }

    @Override
    public Boolean setIfPresent(K key, V value) {
        Ehcache cache = findCache(key.getCategory());
        Serializable rawKey = rawKey(key);
        Element element = new Element(rawKey, value);
        cache.put(element);
        return true;
    }

    @Override
    public Boolean setIfPresent(K key, V value, long timeout, TimeUnit unit) {
        Ehcache cache = findCache(key.getCategory());
        Serializable rawKey = rawKey(key);
        Element element = new Element(rawKey, value);
        element.setTimeToLive(Long.valueOf(TimeoutUtils.toSeconds(timeout, unit)).intValue());
        cache.put(element);
        return true;
    }

    @Override
    public void multiSet(Map<? extends K, ? extends V> map) {
        String category = null;
        List<Element> elements = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            K key = (K) entry.getKey();
            if (null == category) {
                category = key.getCategory();
            } else {
                if (!key.getCategory().equalsIgnoreCase(category)) {
                    throw new KunkkaUnsupportMethodException();
                }
            }
            Serializable rawKey = rawKey((StoreKey) entry.getKey());
            Element element = new Element(rawKey, entry.getValue());
            elements.add(element);
        }
        Ehcache cache = findCache(category);
        cache.putAll(elements);
    }

    @Override
    public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> map) {
        this.multiSet(map);
        return true;
    }

    @Override
    public V get(Object key) {
        if (key instanceof StoreKey) {
            StoreKey storeKey = (StoreKey) key;
            Ehcache cache = findCache(storeKey.getCategory());
            Serializable rawKey = rawKey(storeKey);
            Element element = cache.get(rawKey);
            return (V) (element == null ? null : element.getObjectValue());
        } else {
            throw new KunkkaUnValidKeyException();
        }
    }

    @Override
    public V getAndSet(K key, V value) {
        Ehcache cache = findCache(key.getCategory());
        Serializable rawKey = rawKey(key);
        Element element = new Element(rawKey, value);
        Element existElement = cache.putIfAbsent(element);
        return (V) existElement.getObjectValue();
    }

    @Override
    public List<V> multiGet(Collection<K> keys) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Long increment(K key) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Long increment(K key, long delta) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Double increment(K key, double delta) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Long decrement(K key) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Long decrement(K key, long delta) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Integer append(K key, String value) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public String get(K key, long start, long end) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public void set(K key, V value, long offset) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Long size(K key) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Boolean setBit(K key, long offset, boolean value) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Boolean getBit(K key, long offset) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public List<Long> bitField(K key, BitFieldSubCommands subCommands) {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public RedisOperations<K, V> getOperations() {
        throw new KunkkaUnsupportMethodException();
    }
}

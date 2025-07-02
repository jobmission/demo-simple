package com.example.demo.localcache;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FileBackedCache implements Cache, Serializable {
    private static final Logger log = LoggerFactory.getLogger(FileBackedCache.class);
    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final Map<String, CacheValueWrapper> cache;
    private final String cacheDir;
    private final int maxSize;
    private final long expireMinutes;
    boolean forceClearWhenFull = true;

    public FileBackedCache(String name,
                           String cacheDir,
                           int maxSize,
                           long expireMinutes,
                           boolean forceClearWhenFull) {
        this.name = name;
        this.cache = new ConcurrentHashMap<>();
        this.cacheDir = cacheDir;
        this.maxSize = maxSize;
        this.expireMinutes = expireMinutes;
        this.forceClearWhenFull = forceClearWhenFull;
    }

    public FileBackedCache(String name,
                           String cacheDir,
                           int maxSize,
                           long expireMinutes) {
        this.name = name;
        this.cache = new ConcurrentHashMap<>();
        this.cacheDir = cacheDir;
        this.maxSize = Math.max(maxSize, 4);
        if (expireMinutes <= 0) {
            this.expireMinutes = 60 * 24;
        } else {
            this.expireMinutes = expireMinutes;
        }
    }

    public FileBackedCache(String name,
                           String cacheDir) {
        this.name = name;
        this.cache = new ConcurrentHashMap<>();
        this.cacheDir = cacheDir;
        this.maxSize = 4096;
        this.expireMinutes = 15;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public Object getNativeCache() {
        return cache.entrySet().stream()
            .filter(entry -> !entry.getValue().isExpired())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public ValueWrapper get(Object key) {
        CacheValueWrapper cacheValueWrapper = cache.get(key.toString());
        if (cacheValueWrapper == null || cacheValueWrapper.isExpired()) {
            return null;
        } else {
            return cacheValueWrapper;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper valueWrapper = cache.get(key.toString());
        if (valueWrapper == null) {
            return null;
        } else if (type != null && !type.isInstance(valueWrapper.get())) {
            String typeName = type.getName();
            throw new IllegalStateException("Cached value is not of required type [" + typeName + "]: " + valueWrapper.get());
        } else {
            return (T) valueWrapper.get();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, @NotNull Callable<T> valueLoader) {
        return (T) cache.compute(key.toString(), (k, v) -> {
            try {
                if (v == null || v.isExpired()) {
                    return new CacheValueWrapper(valueLoader.call(), expireMinutes * 60);
                }
                return v;
            } catch (Exception e) {
                throw new ValueRetrievalException(key, valueLoader, e);
            }
        });
    }

    @Override
    public void put(@NotNull Object key, Object value) {
        if (cache.containsKey(key.toString())) {
            cache.put(key.toString(), new CacheValueWrapper(value, expireMinutes * 60));
        } else {
            if (cache.size() >= maxSize) {
                log.info("Cache [{}] is full {}, trying clear expired key", name, cacheSize());
                cleanExpiredEntries();
                if (cache.size() >= maxSize) {
                    log.info("Cache [{}] is still full after cleaning expired keys", name);
                    if (forceClearWhenFull) {
                        int clearCount = (int) Math.ceil(maxSize * 0.1);
                        log.info("Cache [{}] is full, trying force clear {} entries", name, clearCount);
                        List<String> keyList = cache.entrySet().stream()
                            .sorted(Comparator.comparingLong(e -> e.getValue().getExpireTimestamp()))
                            .limit(clearCount)
                            .map(Map.Entry::getKey)
                            .toList();
                        keyList.forEach(this::evict);
                        log.info("after force clear, cache [{}] size is {}", name, cacheSize());
                    } else {
                        throw new RuntimeException("Cache [" + name + "] is full");
                    }
                }
            }
            cache.put(key.toString(), new CacheValueWrapper(value, expireMinutes * 60));
        }
    }

    @Override
    public void evict(Object key) {
        cache.remove(key.toString());
    }

    @Override
    public void clear() {
        cache.clear();
    }

    private void cleanExpiredEntries() {
        cache.entrySet().removeIf(e -> e.getValue().isExpired());
    }

    public Map<String, CacheValueWrapper> getAll() {
        return cache;
    }

    public Map<String, CacheValueWrapper> effectiveMap() {
        return cache.entrySet().stream()
            .filter(entry -> !entry.getValue().isExpired())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public int cacheSize() {
        return cache.size();
    }
}

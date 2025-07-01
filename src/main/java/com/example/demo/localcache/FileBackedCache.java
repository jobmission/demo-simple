package com.example.demo.localcache;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public FileBackedCache(String name,
                           String cacheDir,
                           int maxSize,
                           long expireMinutes) {
        this.name = name;
        this.cache = new ConcurrentHashMap<>();
        this.cacheDir = cacheDir;
        this.maxSize = maxSize;
        this.expireMinutes = expireMinutes;
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
        if (cache.size() >= maxSize) {
            throw new RuntimeException("Cache [" + name + "] is full");
        }
        cache.put(key.toString(), new CacheValueWrapper(value, expireMinutes * 60));
    }

    @Override
    public void evict(Object key) {
        cache.remove(key.toString());
        try {
            Files.deleteIfExists(Paths.get(cacheDir).resolve(key.toString()));
        } catch (IOException e) {
            log.error("Failed to delete cache file for key: {}", key, e);
        }
    }

    @Override
    public void clear() {
        cache.clear();
        try {
            Files.delete(Paths.get(cacheDir, name));
        } catch (IOException e) {
            // Error handling
            log.error("Failed to delete cache file: {}", name, e);
        }
    }

    public Map<String, CacheValueWrapper> getAll() {
        return cache;
    }

    public Map<String, CacheValueWrapper> asMap() {
        return cache.entrySet().stream()
            .filter(entry -> !entry.getValue().isExpired())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public int cacheSize() {
        return cache.size();
    }

    private Object unwrapValue(Object value) {
        if (value instanceof CacheValueWrapper cacheValueWrapper) {
            return cacheValueWrapper.getValue();
        }
        return value;
    }
}

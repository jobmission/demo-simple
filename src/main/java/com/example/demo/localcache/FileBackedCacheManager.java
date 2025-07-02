package com.example.demo.localcache;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class FileBackedCacheManager implements CacheManager, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(FileBackedCacheManager.class);
    private final Map<String, FileBackedCache> cacheStore;
    private final Path cacheDir;
    private final ScheduledExecutorService scheduler;
    private final int maxSize;
    private final long expireMinutes;

    public FileBackedCacheManager(Path cacheDir, Duration flushInterval, Duration clearInterval, int maxSize, long expireMinutes) {
        this.cacheStore = new ConcurrentHashMap<>(16);
        this.cacheDir = cacheDir;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.maxSize = maxSize;
        this.expireMinutes = expireMinutes;
        initializeCache(flushInterval, clearInterval);
    }

    public FileBackedCacheManager(Duration flushInterval, Duration clearInterval) {
        this.cacheStore = new ConcurrentHashMap<>(16);
        this.cacheDir = Paths.get("/data/local_cache");
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.maxSize = 4096;
        this.expireMinutes = 15;
        initializeCache(flushInterval, clearInterval);
    }

    public FileBackedCacheManager() {
        this.cacheStore = new ConcurrentHashMap<>(16);
        this.cacheDir = Paths.get("/data/local_cache");
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.maxSize = 4096;
        this.expireMinutes = 15;
        initializeCache(Duration.ofSeconds(10), Duration.ofSeconds(30));
    }

    private void initializeCache(Duration flushInterval, Duration clearInterval) {
        try {
            Files.createDirectories(cacheDir);
            loadCacheFromDisk();
            scheduler.scheduleAtFixedRate(this::persistCacheToDisk, flushInterval.toMillis(), flushInterval.toMillis(), TimeUnit.MILLISECONDS);
            scheduler.scheduleAtFixedRate(this::cleanExpiredEntries, clearInterval.toSeconds(), clearInterval.toSeconds(), TimeUnit.SECONDS);
        } catch (IOException e) {
            throw new RuntimeException("Cache initialization failed", e);
        }
    }

    private void cleanExpiredEntries() {
        cacheStore.forEach((cacheName, fileBackedCache) -> {
            Map<String, CacheValueWrapper> cache = fileBackedCache.getAll();
            cache.entrySet().removeIf(e -> e.getValue().isExpired());
        });
    }

    private void loadCacheFromDisk() throws IOException {
        try (Stream<Path> paths = Files.walk(cacheDir)) {
            paths.filter(Files::isRegularFile).forEach(this::loadCacheFile);
        }
    }

    private void loadCacheFile(Path file) {
        try {
            FileBackedCache fileBackedCache = deserialize(Files.readAllBytes(file));
            if (fileBackedCache != null && !fileBackedCache.effectiveMap().isEmpty()) {
                log.info("Loaded cache file: {}, cache size: {}", file.getFileName(), fileBackedCache.cacheSize());
                cacheStore.put(file.getFileName().toString(), fileBackedCache);
            } else {
                log.info("loadCacheFile {}, but cache is empty, deleting file", file.getFileName());
                Files.deleteIfExists(file);
            }
        } catch (Exception e) {
            log.error("Failed to load cache file: {}", file, e);
        }
    }

    private void persistCacheToDisk() {
        cacheStore.forEach((cacheName, cache) -> {
            try {
                Path file = cacheDir.resolve(cacheName);
                Files.write(file, serialize(cache));
            } catch (Exception e) {
                log.error("Failed to persist cache : {}", cacheName, e);
            }
        });
    }

    @NotNull
    @Override
    public Cache getCache(@NotNull String name) {
        return cacheStore.computeIfAbsent(name, k -> new FileBackedCache(k, cacheDir.toString(), maxSize, expireMinutes));
    }

    public void addCache(String name, int maxSize, long expireMinutes) {
        cacheStore.put(name, new FileBackedCache(name, cacheDir.toString(), maxSize, expireMinutes));
    }

    @NotNull
    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(cacheStore.keySet());
    }

    @Override
    public void destroy() {
        scheduler.shutdown();
        persistCacheToDisk();
    }

    public void clear() {
        cacheStore.clear();
        try {
            Files.walk(cacheDir).filter(Files::isRegularFile).forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    // Error handling
                    log.error("Failed to delete file: {}", file.getFileName(), e);
                }
            });
            cacheStore.clear();
        } catch (IOException e) {
            log.error("Failed to clear cache directory: {}", cacheDir, e);
        }
    }

    private byte[] serialize(FileBackedCache fileBackedCache) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(fileBackedCache);
            return bos.toByteArray();
        }
    }

    private FileBackedCache deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data); ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (FileBackedCache) ois.readObject();
        }
    }
}

package com.example.demo.config;

import com.example.demo.localcache.FileBackedCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;
import java.time.Duration;

@Configuration
public class FileBackedCacheConfig {
    private static final Duration FLUSH_INTERVAL = Duration.ofSeconds(10);
    private static final Duration CLEAR_INTERVAL = Duration.ofSeconds(30);

    @Value("${dir.local-cache:/data/local_cache}")
    String localCacheDir;

    @Bean
    public FileBackedCacheManager cacheManager() {
        FileBackedCacheManager cacheManager = new FileBackedCacheManager(
            Paths.get(localCacheDir),
            FLUSH_INTERVAL,
            CLEAR_INTERVAL,
            4096,  // 最大缓存数量, 2的指数倍
            15     // 分钟过期时间
        );
        cacheManager.addCache("testCache", 5, 10); // 初始化一个缓存
        return cacheManager;
    }
}


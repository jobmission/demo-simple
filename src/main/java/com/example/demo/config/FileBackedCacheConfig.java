package com.example.demo.config;

import com.example.demo.localcache.FileBackedCacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Paths;
import java.time.Duration;

@Configuration
@EnableCaching
@EnableScheduling
public class FileBackedCacheConfig {
    private static final String CACHE_DIR = "/tmp/cache"; // 缓存目录
    private static final Duration FLUSH_INTERVAL = Duration.ofSeconds(10);
    private static final Duration CLEAR_INTERVAL = Duration.ofSeconds(30);

    @Bean
    public FileBackedCacheManager cacheManager() {
        return new FileBackedCacheManager(
            Paths.get(CACHE_DIR),
            FLUSH_INTERVAL,
            CLEAR_INTERVAL,
            1000,  // 最大缓存数量
            3     // 分钟过期时间
        );
    }
}


package com.example.demo.config;

public enum CachesEnum {
    /**
     * 使用默认值
     */
    DefaultCache,
    /**
     * 指定过期时间和最大容量
     */
    KeywordCache(60 * 60 * 2, 10000);

    CachesEnum() {
    }

    CachesEnum(int ttl) {
        this.ttl = ttl;
    }

    CachesEnum(int ttl, int maxSize) {
        this.ttl = ttl;
        this.maxSize = maxSize;
    }

    private int maxSize = 100000;
    private int ttl = 60 * 60 * 2;

    public int getMaxSize() {
        return maxSize;
    }

    public int getTtl() {
        return ttl;
    }
}

package com.example.demo.localcache;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class CacheValueWrapper implements Cache.ValueWrapper, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Nullable
    private final Object value;
    private final long expireTimestamp;

    public CacheValueWrapper(@Nullable Object value, long ttlSeconds) {
        this.value = value;
        this.expireTimestamp = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    @Override
    @Nullable
    public Object get() {
        return isExpired() ? null : value;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return (this == other || (other instanceof Cache.ValueWrapper wrapper && Objects.equals(get(), wrapper.get())));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    @Override
    public String toString() {
        return "ValueWrapper for [" + this.value + "]";
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > getExpireTimestamp();
    }

    public Object getValue() {
        return isExpired() ? null : value;
    }

    public long getExpireTimestamp() {
        return expireTimestamp;
    }
}

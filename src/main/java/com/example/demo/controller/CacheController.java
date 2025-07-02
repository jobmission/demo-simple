package com.example.demo.controller;

import com.example.demo.localcache.FileBackedCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cache")
@RestController
public class CacheController implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(CacheController.class);

    CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping("/put")
    public String put(@RequestParam(value = "cacheName", required = false, defaultValue = "testCache") String cacheName,
                      @RequestParam(value = "key", required = false, defaultValue = "abc") String key,
                      @RequestParam(value = "value", required = false, defaultValue = "abcValue") String value) {

        cacheManager.getCache(cacheName).put(key, value);
        return "ok";
    }

    @GetMapping("/get")
    public Object get(@RequestParam(value = "cacheName", required = false, defaultValue = "testCache") String cacheName,
                      @RequestParam(value = "key", required = false, defaultValue = "abc") String key) {
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(cacheName).get(key);
        return valueWrapper == null ? null : valueWrapper.get();
    }

    @GetMapping("/list")
    public Object list(@RequestParam(value = "cacheName", required = false, defaultValue = "testCache") String cacheName) {
        return ((FileBackedCache) (cacheManager.getCache(cacheName))).effectiveMap();
    }


    @Override
    public void afterPropertiesSet() {
        log.info("afterPropertiesSet");
    }

}

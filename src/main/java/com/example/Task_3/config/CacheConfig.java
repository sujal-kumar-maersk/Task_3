package com.example.Task_3.config;

import com.example.Task_3.model.Employee;
import com.example.Task_3.service.Implementation.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {
    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 15000,initialDelay = 15000)
    public void clearCache(){
        printCacheContents("applicationCache");
        cacheManager.getCacheNames().parallelStream().forEach(
                name -> Objects.requireNonNull(cacheManager.getCache(name)).clear()
        );
    }

    public void printCacheContents(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if(cache instanceof ConcurrentMapCache){
            Map<?,?> nativeCache = ((ConcurrentMapCache) cache).getNativeCache();
            System.out.println("Contents of cache '" + cacheName + "':");
            nativeCache.forEach((k,v) -> System.out.println(k + " => " + v));
        }else{
            System.out.println("Cache type not supported: " + cache.getClass());
        }
    }



}

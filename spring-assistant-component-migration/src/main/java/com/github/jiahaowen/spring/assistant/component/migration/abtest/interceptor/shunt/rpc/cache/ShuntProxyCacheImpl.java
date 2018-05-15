package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.rpc.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * rpc代理缓存实现类
 *
 * @author jiahaowen.jhw
 * @version $Id: ShuntProxyCacheImpl.java, v 0.1 2016-12-28 上午11:41 jiahaowen.jhw Exp $
 */
@Component
public class ShuntProxyCacheImpl implements ShuntProxyCache {

    /** 默认日志类 */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShuntProxyCacheImpl.class);

    /** 缓存最大容量 */
    private static final int MAX_CACHE_SIZE = 100;

    /** 缓存 */
    private final Cache<String, Object> cache =
            CacheBuilder.newBuilder().maximumSize(MAX_CACHE_SIZE).recordStats().build();

    /**
     * 取
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * 入
     *
     * @param key
     * @param object
     */
    @Override
    public void put(String key, Object object) {
        cache.put(key, object);
    }

    /**
     * 删
     *
     * @param key
     */
    @Override
    public void delete(String key) {
        cache.invalidate(key);
    }
}

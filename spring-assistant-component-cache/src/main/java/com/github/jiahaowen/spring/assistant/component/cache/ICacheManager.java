package com.github.jiahaowen.spring.assistant.component.cache;

import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheKeyDTO;
import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheWrapper;
import com.github.jiahaowen.spring.assistant.component.cache.exception.CacheCenterConnectionException;
import java.lang.reflect.Method;

/**
 * 缓存管理
 * @author jiahaowen
 */
public interface ICacheManager {

    /**
     * 往缓存写数据
     * @param cacheKey 缓存Key
     * @param result 缓存数据
     * @param method Method
     * @param args args
     * @throws CacheCenterConnectionException 缓存异常
     */
    void setCache(final CacheKeyDTO cacheKey, final CacheWrapper<Object> result,
        final Method method, final Object args[]) throws CacheCenterConnectionException;

    /**
     * 根据缓存Key获得缓存中的数据
     * @param key 缓存key
     * @param method Method
     * @param args args
     * @return 缓存数据
     * @throws CacheCenterConnectionException 缓存异常
     */
    CacheWrapper<Object> get(final CacheKeyDTO key, final Method method, final Object args[]) throws CacheCenterConnectionException;

    /**
     * 删除缓存
     * @param key 缓存key
     * @throws CacheCenterConnectionException 缓存异常
     */
    void delete(final CacheKeyDTO key) throws CacheCenterConnectionException;

}

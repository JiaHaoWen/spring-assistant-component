package com.github.jiahaowen.spring.assistant.component.cache.memcache;

import com.github.jiahaowen.spring.assistant.component.cache.ICacheManager;
import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheKeyDTO;
import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheWrapper;
import com.github.jiahaowen.spring.assistant.component.cache.exception.CacheCenterConnectionException;
import java.lang.reflect.Method;
import net.spy.memcached.MemcachedClient;

/**
 * memcache缓存管理
 *
 * @author: jiahaowen
 */
public class MemcachedCacheManager implements ICacheManager {

    private MemcachedClient memcachedClient;

    public MemcachedCacheManager() {}

    @Override
    public void setCache(
            final CacheKeyDTO cacheKeyTO,
            final CacheWrapper<Object> result,
            final Method method,
            final Object args[])
            throws CacheCenterConnectionException {
        if (null == cacheKeyTO) {
            return;
        }
        String cacheKey = cacheKeyTO.getCacheKey();
        if (null == cacheKey || cacheKey.length() == 0) {
            return;
        }
        String hfield = cacheKeyTO.getHfield();
        if (null != hfield && hfield.length() > 0) {
            throw new RuntimeException("memcached does not support hash cache.");
        }
        if (result.getExpire() >= 0) {
            memcachedClient.set(cacheKey, result.getExpire(), result);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public CacheWrapper<Object> get(
            final CacheKeyDTO cacheKeyTO, Method method, final Object args[])
            throws CacheCenterConnectionException {
        if (null == cacheKeyTO) {
            return null;
        }
        String cacheKey = cacheKeyTO.getCacheKey();
        if (null == cacheKey || cacheKey.length() == 0) {
            return null;
        }
        String hfield = cacheKeyTO.getHfield();
        if (null != hfield && hfield.length() > 0) {
            throw new RuntimeException("memcached does not support hash cache.");
        }
        return (CacheWrapper<Object>) memcachedClient.get(cacheKey);
    }

    /**
     * 通过组成Key直接删除
     *
     * @param cacheKeyTO 缓存Key
     */
    @Override
    public void delete(CacheKeyDTO cacheKeyTO) throws CacheCenterConnectionException {
        if (null == memcachedClient || null == cacheKeyTO) {
            return;
        }
        String cacheKey = cacheKeyTO.getCacheKey();
        if (null == cacheKey || cacheKey.length() == 0) {
            return;
        }
        String hfield = cacheKeyTO.getHfield();
        if (null != hfield && hfield.length() > 0) {
            throw new RuntimeException("memcached does not support hash cache.");
        }
        try {
            String allKeysPattern = "*";
            if (allKeysPattern.equals(cacheKey)) {
                memcachedClient.flush();
            } else {
                memcachedClient.delete(cacheKey);
            }
        } catch (Exception e) {
        }
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}

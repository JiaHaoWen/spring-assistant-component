package com.github.jiahaowen.spring.assistant.component.cache;

import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheKeyDTO;
import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheWrapper;

/**
 * 缓存更新
 * @author jiahaowen
 */
public interface ChangeListener {

    /**
     * 缓存更新
     * @param cacheKey 缓存Key
     * @param newVal 新缓存值
     */
    void update(CacheKeyDTO cacheKey, CacheWrapper<Object> newVal);

    /**
     * 缓存删除
     * @param cacheKey 缓存Key
     */
    void delete(CacheKeyDTO cacheKey);
}

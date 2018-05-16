package com.github.jiahaowen.spring.assistant.component.cache;

import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheKeyDTO;
import com.github.jiahaowen.spring.assistant.component.cache.type.CacheOpType;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author jiahaowen
 */
@Component
public class CacheHelper {

    private static final ThreadLocal<CacheOpType> OP_TYPE=new ThreadLocal<CacheOpType>();

    private static final ThreadLocal<Set<CacheKeyDTO>> DELETE_CACHE_KEYS=new ThreadLocal<Set<CacheKeyDTO>>();

    /**
     * 获取CacheOpType
     * @return ThreadLocal中设置的CacheOpType
     */
    public static CacheOpType getCacheOpType() {
        return OP_TYPE.get();
    }

    /**
     * 设置CacheOpType
     * @param opType CacheOpType
     */
    public static void setCacheOpType(CacheOpType opType) {
        OP_TYPE.set(opType);
    }

    /**
     * 移除CacheOpType
     */
    public static void clearCacheOpType() {
        OP_TYPE.remove();
    }

    public static void initDeleteCacheKeysSet() {
        Set<CacheKeyDTO> set=DELETE_CACHE_KEYS.get();
        if(null == set) {
            set=new HashSet<CacheKeyDTO>();
            DELETE_CACHE_KEYS.set(set);
        }
    }

    public static Set<CacheKeyDTO> getDeleteCacheKeysSet() {
        return DELETE_CACHE_KEYS.get();
    }

    public static boolean addDeleteCacheKey(CacheKeyDTO key) {
        Set<CacheKeyDTO> set=DELETE_CACHE_KEYS.get();
        if(null != set) {
            set.add(key);
            return true;
        }
        return false;
    }

    public static void clearDeleteCacheKeysSet() {
        DELETE_CACHE_KEYS.remove();
    }
}

package com.github.jiahaowen.spring.assistant.component.cache.redis;

import com.github.jiahaowen.spring.assistant.component.cache.ICacheManager;
import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheKeyDTO;
import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheWrapper;
import com.github.jiahaowen.spring.assistant.component.cache.exception.CacheCenterConnectionException;
import com.github.jiahaowen.spring.assistant.component.cache.serializer.ISerializer;
import com.github.jiahaowen.spring.assistant.component.cache.serializer.StringSerializer;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author: jiahaowen */
public abstract class AbstractRedisCacheManager implements ICacheManager {

    private static final Logger log = LoggerFactory.getLogger(AbstractRedisCacheManager.class);

    private static final StringSerializer KEY_SERIALIZER = new StringSerializer();

    /** Hash的缓存时长：等于0时永久缓存；大于0时，主要是为了防止一些已经不用的缓存占用内存;hashExpire小于0时，则使用@Cache中设置的expire值（默认值为-1）。 */
    private int hashExpire = -1;

    private final ISerializer<Object> serializer;

    public AbstractRedisCacheManager(ISerializer<Object> serializer) {
        this.serializer = serializer;
    }

    protected abstract IRedis getRedis(String cacheKey);

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
        try (IRedis redis = getRedis(cacheKey)) {
            int expire = result.getExpire();
            String hfield = cacheKeyTO.getHfield();
            if (null == hfield || hfield.length() == 0) {
                if (expire == 0) {
                    redis.set(KEY_SERIALIZER.serialize(cacheKey), serializer.serialize(result));
                } else if (expire > 0) {
                    redis.setex(
                            KEY_SERIALIZER.serialize(cacheKey),
                            expire,
                            serializer.serialize(result));
                }
            } else {
                hashSet(redis, cacheKey, hfield, result);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void hashSet(IRedis redis, String cacheKey, String hfield, CacheWrapper<Object> result)
            throws Exception {
        byte[] key = KEY_SERIALIZER.serialize(cacheKey);
        byte[] field = KEY_SERIALIZER.serialize(hfield);
        byte[] val = serializer.serialize(result);
        int hExpire;
        if (hashExpire < 0) {
            hExpire = result.getExpire();
        } else {
            hExpire = hashExpire;
        }
        if (hExpire == 0) {
            redis.hset(key, field, val);
        } else if (hExpire > 0) {
            redis.hset(key, field, val, hExpire);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public CacheWrapper<Object> get(
            final CacheKeyDTO cacheKeyTO, final Method method, final Object args[])
            throws CacheCenterConnectionException {
        if (null == cacheKeyTO) {
            return null;
        }
        String cacheKey = cacheKeyTO.getCacheKey();
        if (null == cacheKey || cacheKey.length() == 0) {
            return null;
        }
        CacheWrapper<Object> res = null;
        try (IRedis redis = getRedis(cacheKey)) {
            byte bytes[] = null;
            String hfield = cacheKeyTO.getHfield();
            if (null == hfield || hfield.length() == 0) {
                bytes = redis.get(KEY_SERIALIZER.serialize(cacheKey));
            } else {
                bytes =
                        redis.hget(
                                KEY_SERIALIZER.serialize(cacheKey),
                                KEY_SERIALIZER.serialize(hfield));
            }
            Type returnType = null;
            if (null != method) {
                returnType = method.getGenericReturnType();
            }
            res = (CacheWrapper<Object>) serializer.deserialize(bytes, returnType);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return res;
    }

    /**
     * 根据缓存Key删除缓存
     *
     * @param cacheKeyTO 缓存Key
     */
    @Override
    public void delete(CacheKeyDTO cacheKeyTO) throws CacheCenterConnectionException {
        if (null == cacheKeyTO) {
            return;
        }
        String cacheKey = cacheKeyTO.getCacheKey();
        if (null == cacheKey || cacheKey.length() == 0) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("delete cache {}", cacheKey);
        }
        try (IRedis redis = getRedis(cacheKey)) {
            String hfield = cacheKeyTO.getHfield();
            if (null == hfield || hfield.length() == 0) {
                redis.del(KEY_SERIALIZER.serialize(cacheKey));
            } else {
                redis.hdel(KEY_SERIALIZER.serialize(cacheKey), KEY_SERIALIZER.serialize(hfield));
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public int getHashExpire() {
        return hashExpire;
    }

    public void setHashExpire(int hashExpire) {
        if (hashExpire < 0) {
            return;
        }
        this.hashExpire = hashExpire;
    }
}

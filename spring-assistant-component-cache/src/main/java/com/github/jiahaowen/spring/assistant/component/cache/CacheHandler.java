package com.github.jiahaowen.spring.assistant.component.cache;

import com.github.jiahaowen.spring.assistant.component.cache.annotation.Cache;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.CacheDelete;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.CacheDeleteKey;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.CacheDeleteTransactional;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.ExCache;
import com.github.jiahaowen.spring.assistant.component.cache.aop.CacheAopProxyChain;
import com.github.jiahaowen.spring.assistant.component.cache.aop.DeleteCacheAopProxyChain;
import com.github.jiahaowen.spring.assistant.component.cache.aop.DeleteCacheTransactionalAopProxyChain;
import com.github.jiahaowen.spring.assistant.component.cache.clone.ICloner;
import com.github.jiahaowen.spring.assistant.component.cache.dto.AutoLoadConfig;
import com.github.jiahaowen.spring.assistant.component.cache.dto.AutoLoadDTO;
import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheKeyDTO;
import com.github.jiahaowen.spring.assistant.component.cache.dto.CacheWrapper;
import com.github.jiahaowen.spring.assistant.component.cache.dto.ProcessingDTO;
import com.github.jiahaowen.spring.assistant.component.cache.exception.CacheCenterConnectionException;
import com.github.jiahaowen.spring.assistant.component.cache.lock.ILock;
import com.github.jiahaowen.spring.assistant.component.cache.script.AbstractScriptParser;
import com.github.jiahaowen.spring.assistant.component.cache.type.CacheOpType;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理AOP
 *
 * @author jiahaowen
 */
@Component
public class CacheHandler {

    private static final Logger logger = LoggerFactory.getLogger(CacheHandler.class);

    /** 正在处理中的请求 */
    public final ConcurrentHashMap<CacheKeyDTO, ProcessingDTO> processing;

    //TODO:如何自动注入某个具体实现？？？
    private final ICacheManager cacheManager;

    private final AutoLoadConfig config;

    private final ICloner cloner;

    private final AutoLoadHandler autoLoadHandler;

    /** 表达式解析器 */
    private final AbstractScriptParser scriptParser;

    private final RefreshHandler refreshHandler;

    /** 分布式锁 */
    private ILock lock;

    private ChangeListener changeListener;

    public CacheHandler(
            ICacheManager cacheManager,
            AbstractScriptParser scriptParser,
            AutoLoadConfig config,
            ICloner cloner) {
        this.processing =
                new ConcurrentHashMap<CacheKeyDTO, ProcessingDTO>(config.getProcessingMapSize());
        this.cacheManager = cacheManager;
        this.config = config;
        this.cloner = cloner;
        this.autoLoadHandler = new AutoLoadHandler(this, config);
        this.scriptParser = scriptParser;
        registerFunction(config.getFunctions());
        refreshHandler = new RefreshHandler(this, config);
    }

    /**
     * 从数据源中获取最新数据，并写入缓存。注意：这里不使用“拿来主义”机制，是因为当前可能是更新数据的方法。
     *
     * @param pjp CacheAopProxyChain
     * @param cache Cache注解
     * @return 最新数据
     * @throws Throwable 异常
     */
    private Object writeOnly(CacheAopProxyChain pjp, Cache cache) throws Throwable {
        DataLoaderFactory factory = DataLoaderFactory.getInstance();
        DataLoader dataLoader = factory.getDataLoader();
        CacheWrapper<Object> cacheWrapper;
        try {
            cacheWrapper = dataLoader.init(pjp, cache, this).getData().getCacheWrapper();
        } catch (Throwable e) {
            throw e;
        } finally {
            factory.returnObject(dataLoader);
        }
        Object result = cacheWrapper.getCacheObject();
        Object[] arguments = pjp.getArgs();
        if (scriptParser.isCacheable(cache, pjp.getTarget(), arguments, result)) {
            CacheKeyDTO cacheKey = getCacheKey(pjp, cache, result);
            AutoLoadDTO autoLoadTO =
                    autoLoadHandler.getAutoLoadTO(cacheKey); // 注意：这里只能获取AutoloadTO，不能生成AutoloadTO
            try {
                writeCache(pjp, pjp.getArgs(), cache, cacheKey, cacheWrapper);
                if (null != autoLoadTO) {
                    autoLoadTO
                            .setLastLoadTime(cacheWrapper.getLastLoadTime()) // 同步加载时间
                            .setExpire(cacheWrapper.getExpire()); // 同步过期时间
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return result;
    }

    /**
     * 获取CacheOpType，从三个地方获取：<br>
     * 1. Cache注解中获取；<br>
     * 2. 从ThreadLocal中获取；<br>
     * 3. 从参数中获取；<br>
     * 上面三者的优先级：从低到高。
     *
     * @param cache 注解
     * @param arguments 参数
     * @return CacheOpType
     */
    private CacheOpType getCacheOpType(Cache cache, Object[] arguments) {
        CacheOpType opType = cache.opType();
        CacheOpType tmpOpType = CacheHelper.getCacheOpType();
        if (null != tmpOpType) {
            opType = tmpOpType;
        }
        if (null != arguments && arguments.length > 0) {
            for (Object tmp : arguments) {
                if (null != tmp && tmp instanceof CacheOpType) {
                    opType = (CacheOpType) tmp;
                    break;
                }
            }
        }
        if (null == opType) {
            opType = CacheOpType.READ_WRITE;
        }
        return opType;
    }

    /**
     * 处理@Cache 拦截
     *
     * @param pjp 切面
     * @param cache 注解
     * @return T 返回值
     * @throws Exception 异常
     */
    public Object proceed(CacheAopProxyChain pjp, Cache cache) throws Throwable {
        Object[] arguments = pjp.getArgs();
        CacheOpType opType = getCacheOpType(cache, arguments);
        logger.trace(
                "CacheHandler.proceed-->{}.{}--{})",
                pjp.getTarget().getClass().getName(),
                pjp.getMethod().getName(),
                opType.name());

        if (opType == CacheOpType.WRITE) {
            return writeOnly(pjp, cache);
        } else if (opType == CacheOpType.LOAD) {
            return getData(pjp);
        }

        if (!scriptParser.isCacheable(cache, pjp.getTarget(), arguments)) { // 如果不进行缓存，则直接返回数据
            return getData(pjp);
        }

        CacheKeyDTO cacheKey = getCacheKey(pjp, cache);
        if (null == cacheKey) {
            return getData(pjp);
        }
        Method method = pjp.getMethod();
        CacheWrapper<Object> cacheWrapper = null;
        try {
            cacheWrapper = this.get(cacheKey, method, arguments); // 从缓存中获取数据
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        logger.trace(
                "cache key:{}, cache data is null {} ",
                cacheKey.getCacheKey(),
                null == cacheWrapper);

        if (opType == CacheOpType.READ_ONLY) {
            return null == cacheWrapper ? null : cacheWrapper.getCacheObject();
        }

        if (null != cacheWrapper && !cacheWrapper.isExpired()) {
            AutoLoadDTO autoLoadTO = autoLoadHandler.putIfAbsent(cacheKey, pjp, cache, cacheWrapper);
            if (null != autoLoadTO) { // 同步最后加载时间
                autoLoadTO
                        .setLastRequestTime(System.currentTimeMillis()) //
                        .setLastLoadTime(cacheWrapper.getLastLoadTime()) // 同步加载时间
                        .setExpire(cacheWrapper.getExpire()); // 同步过期时间
            } else { // 如果缓存快要失效，则自动刷新
                refreshHandler.doRefresh(pjp, cache, cacheKey, cacheWrapper);
            }
            return cacheWrapper.getCacheObject();
        }
        DataLoaderFactory factory = DataLoaderFactory.getInstance();
        DataLoader dataLoader = factory.getDataLoader();
        CacheWrapper<Object> newCacheWrapper = null;
        long loadDataUseTime = 0;
        boolean isFirst;
        try {
            newCacheWrapper =
                    dataLoader.init(pjp, cacheKey, cache, this).loadData().getCacheWrapper();
            isFirst = dataLoader.isFirst();
            loadDataUseTime = dataLoader.getLoadDataUseTime();
        } catch (Throwable e) {
            throw e;
        } finally {
            factory.returnObject(dataLoader);
        }
        AutoLoadDTO autoLoadTO = null;

        if (isFirst) {
            autoLoadTO = autoLoadHandler.putIfAbsent(cacheKey, pjp, cache, newCacheWrapper);
            try {
                writeCache(pjp, pjp.getArgs(), cache, cacheKey, newCacheWrapper);
                if (null != autoLoadTO) { // 同步最后加载时间
                    autoLoadTO
                            .setLastRequestTime(System.currentTimeMillis()) //
                            .setLastLoadTime(newCacheWrapper.getLastLoadTime()) // 同步加载时间
                            .setExpire(newCacheWrapper.getExpire()) // 同步过期时间
                            .addUseTotalTime(loadDataUseTime); // 统计用时
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        return newCacheWrapper.getCacheObject();
    }

    /**
     * 处理@CacheDelete 拦截
     *
     * @param jp 切点
     * @param cacheDelete 拦截到的注解
     * @param retVal 返回值
     */
    public void deleteCache(DeleteCacheAopProxyChain jp, CacheDelete cacheDelete, Object retVal)
            throws Throwable {
        Object[] arguments = jp.getArgs();
        CacheDeleteKey[] keys = cacheDelete.value();
        if (null == keys || keys.length == 0) {
            return;
        }
        Object target = jp.getTarget();
        String methodName = jp.getMethod().getName();
        try {
            for (int i = 0; i < keys.length; i++) {
                CacheDeleteKey keyConfig = keys[i];
                String[] tempKeys = keyConfig.value();
                String tempHfield = keyConfig.hfield();
                if (!scriptParser.isCanDelete(keyConfig, arguments, retVal)) {
                    continue;
                }
                for (String tempKey : tempKeys) {
                    CacheKeyDTO key =
                            getCacheKey(
                                    target,
                                    methodName,
                                    arguments,
                                    tempKey,
                                    tempHfield,
                                    retVal,
                                    true);
                    if (null != key && !CacheHelper.addDeleteCacheKey(key)) {
                        this.delete(key);
                        this.getAutoLoadHandler().resetAutoLoadLastLoadTime(key);
                    }
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 用于处理事务下，事务处理完后才删除缓存，避免因事务失败造成缓存中的数据不一致问题。
     *
     * @param pjp 切面
     * @param cacheDeleteTransactional 注解
     * @return Object 返回值
     * @throws Exception 异常
     */
    public Object proceedDeleteCacheTransactional(
            DeleteCacheTransactionalAopProxyChain pjp,
            CacheDeleteTransactional cacheDeleteTransactional)
            throws Throwable {
        Object result = null;
        Set<CacheKeyDTO> set0 = CacheHelper.getDeleteCacheKeysSet();
        boolean isStart = null == set0;
        if (!cacheDeleteTransactional.useCache()) {
            CacheHelper.setCacheOpType(CacheOpType.LOAD); // 在事务环境下尽量直接去数据源加载数据，而不是从缓存中加载，减少数据不一致的可能
        }
        try {
            CacheHelper.initDeleteCacheKeysSet(); // 初始化Set
            result = pjp.doProxyChain();
        } catch (Throwable e) {
            throw e;
        } finally {
            CacheHelper.clearCacheOpType();
        }
        Set<CacheKeyDTO> set = CacheHelper.getDeleteCacheKeysSet();
        if (isStart) {
            try {
                if (null != set && set.size() > 0) {
                    for (CacheKeyDTO key : set) {
                        this.delete(key);
                        logger.trace("proceedDeleteCacheTransactional delete-->{}", key);
                    }
                } else {
                    logger.warn("proceedDeleteCacheTransactional: key set is empty!");
                }
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
                throw e; // 抛出异常，让事务回滚，避免数据库和缓存双写不一致问题
            } finally {
                CacheHelper.clearDeleteCacheKeysSet();
            }
        }
        return result;
    }

    /**
     * 直接加载数据（加载后的数据不往缓存放）
     *
     * @param pjp CacheAopProxyChain
     * @return Object
     * @throws Throwable
     */
    private Object getData(CacheAopProxyChain pjp) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();
            Object[] arguments = pjp.getArgs();
            Object result = pjp.doProxyChain(arguments);
            long useTime = System.currentTimeMillis() - startTime;
            if (config.isPrintSlowLog() && useTime >= config.getSlowLoadTime()) {
                String className = pjp.getTarget().getClass().getName();
                logger.error("{}.{}, use time:{}ms", className, pjp.getMethod().getName(), useTime);
            }
            return result;
        } catch (Throwable e) {
            throw e;
        }
    }

    public void writeCache(
            CacheAopProxyChain pjp,
            Object[] arguments,
            Cache cache,
            CacheKeyDTO cacheKey,
            CacheWrapper<Object> cacheWrapper)
            throws Exception {
        if (null == cacheKey) {
            return;
        }
        Method method = pjp.getMethod();
        if (cacheWrapper.getExpire() >= 0) {
            this.setCache(cacheKey, cacheWrapper, method, arguments);
        }
        ExCache[] exCaches = cache.exCache();
        if (null == exCaches || exCaches.length == 0) {
            return;
        }

        Object result = cacheWrapper.getCacheObject();
        Object target = pjp.getTarget();
        for (ExCache exCache : exCaches) {
            try {
                if (!scriptParser.isCacheable(exCache, pjp.getTarget(), arguments, result)) {
                    continue;
                }
                CacheKeyDTO exCacheKey = getCacheKey(pjp, arguments, exCache, result);
                if (null == exCacheKey) {
                    continue;
                }
                Object exResult = null;
                if (null == exCache.cacheObject() || exCache.cacheObject().length() == 0) {
                    exResult = result;
                } else {
                    exResult =
                            scriptParser.getElValue(
                                    exCache.cacheObject(),
                                    target,
                                    arguments,
                                    result,
                                    true,
                                    Object.class);
                }

                int exCacheExpire =
                        scriptParser.getRealExpire(
                                exCache.expire(), exCache.expireExpression(), arguments, exResult);
                CacheWrapper<Object> exCacheWrapper =
                        new CacheWrapper<Object>(exResult, exCacheExpire);
                AutoLoadDTO tmpAutoLoadTO = this.autoLoadHandler.getAutoLoadTO(exCacheKey);
                if (exCacheExpire >= 0) {
                    this.setCache(exCacheKey, exCacheWrapper, method, arguments);
                    if (null != tmpAutoLoadTO) {
                        tmpAutoLoadTO
                                .setExpire(exCacheExpire) //
                                .setLastLoadTime(exCacheWrapper.getLastLoadTime()); //
                    }
                }

            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    public void destroy() {
        autoLoadHandler.shutdown();
        refreshHandler.shutdown();
        logger.trace("cache destroy ... ... ...");
    }

    /**
     * 生成缓存KeyTO
     *
     * @param target 类名
     * @param methodName 方法名
     * @param arguments 参数
     * @param keyExpression key表达式
     * @param hfieldExpression hfield表达式
     * @param result 执行实际方法的返回值
     * @return CacheKeyDTO
     */
    private CacheKeyDTO getCacheKey(
            Object target,
            String methodName,
            Object[] arguments,
            String keyExpression,
            String hfieldExpression,
            Object result,
            boolean hasRetVal) {
        String key = null;
        String hfield = null;
        if (null != keyExpression && keyExpression.trim().length() > 0) {
            try {
                key =
                        scriptParser.getDefinedCacheKey(
                                keyExpression, target, arguments, result, hasRetVal);
                if (null != hfieldExpression && hfieldExpression.trim().length() > 0) {
                    hfield =
                            scriptParser.getDefinedCacheKey(
                                    hfieldExpression, target, arguments, result, hasRetVal);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        } else {
            key = CacheUtil.getDefaultCacheKey(target.getClass().getName(), methodName, arguments);
        }
        if (null == key || key.trim().length() == 0) {
            logger.error("{}.{}; cache key is empty", target.getClass().getName(), methodName);
            return null;
        }
        return new CacheKeyDTO(config.getNamespace(), key, hfield);
    }

    /**
     * 生成缓存 Key
     *
     * @param pjp
     * @param cache
     * @return String 缓存Key
     */
    private CacheKeyDTO getCacheKey(CacheAopProxyChain pjp, Cache cache) {
        Object target = pjp.getTarget();
        String methodName = pjp.getMethod().getName();
        Object[] arguments = pjp.getArgs();
        String keyExpression = cache.key();
        String hfieldExpression = cache.hfield();
        return getCacheKey(
                target, methodName, arguments, keyExpression, hfieldExpression, null, false);
    }

    /**
     * 生成缓存 Key
     *
     * @param pjp
     * @param cache
     * @param result 执行结果值
     * @return 缓存Key
     */
    private CacheKeyDTO getCacheKey(CacheAopProxyChain pjp, Cache cache, Object result) {
        Object target = pjp.getTarget();
        String methodName = pjp.getMethod().getName();
        Object[] arguments = pjp.getArgs();
        String keyExpression = cache.key();
        String hfieldExpression = cache.hfield();
        return getCacheKey(
                target, methodName, arguments, keyExpression, hfieldExpression, result, true);
    }

    /**
     * 生成缓存 Key
     *
     * @param pjp
     * @param arguments
     * @param exCache
     * @param result 执行结果值
     * @return 缓存Key
     */
    private CacheKeyDTO getCacheKey(
            CacheAopProxyChain pjp, Object[] arguments, ExCache exCache, Object result) {
        Object target = pjp.getTarget();
        String methodName = pjp.getMethod().getName();
        String keyExpression = exCache.key();
        if (null == keyExpression || keyExpression.trim().length() == 0) {
            return null;
        }
        String hfieldExpression = exCache.hfield();
        return getCacheKey(
                target, methodName, arguments, keyExpression, hfieldExpression, result, true);
    }

    public AutoLoadHandler getAutoLoadHandler() {
        return this.autoLoadHandler;
    }

    public AbstractScriptParser getScriptParser() {
        return scriptParser;
    }

    private void registerFunction(Map<String, String> funcs) {
        if (null == scriptParser) {
            return;
        }
        if (null == funcs || funcs.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<String, String>> it = funcs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            try {
                String name = entry.getKey();
                Class<?> cls = Class.forName(entry.getValue());
                Method method = cls.getDeclaredMethod(name, new Class[] {Object.class});
                scriptParser.addFunction(name, method);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public ILock getLock() {
        return lock;
    }

    public void setLock(ILock lock) {
        this.lock = lock;
    }

    public void setCache(
            CacheKeyDTO cacheKey, CacheWrapper<Object> result, Method method, Object[] args)
            throws CacheCenterConnectionException {
        cacheManager.setCache(cacheKey, result, method, args);
        if (null != changeListener) {
            changeListener.update(cacheKey, result);
        }
    }

    public CacheWrapper<Object> get(CacheKeyDTO key, Method method, Object[] args)
            throws CacheCenterConnectionException {
        return cacheManager.get(key, method, args);
    }

    public void delete(CacheKeyDTO key) throws CacheCenterConnectionException {
        cacheManager.delete(key);
        if (null != changeListener) {
            changeListener.delete(key);
        }
    }

    public ICloner getCloner() {
        return cloner;
    }

    public AutoLoadConfig getAutoLoadConfig() {
        return this.config;
    }

    public ChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }
}

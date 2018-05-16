package com.github.jiahaowen.spring.assistant.component.cache.spring.interceptor;

import com.github.jiahaowen.spring.assistant.component.cache.CacheHandler;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.Cache;
import com.github.jiahaowen.spring.assistant.component.cache.spring.autoconfigure.AutoloadCacheProperties;
import com.github.jiahaowen.spring.assistant.component.cache.spring.interceptor.aopproxy.CacheAopProxy;
import com.github.jiahaowen.spring.assistant.component.cache.util.AopUtil;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

/**
 * 对@Cache 拦截注解
 *
 * @author jiahaowen
 */
public class CacheMethodInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CacheMethodInterceptor.class);

    private final CacheHandler cacheHandler;

    private final AutoloadCacheProperties config;

    public CacheMethodInterceptor(CacheHandler cacheHandler, AutoloadCacheProperties config) {
        this.cacheHandler = cacheHandler;
        this.config = config;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!this.config.isEnable()) {
            return invocation.proceed();
        }
        Class<?> cls = AopUtil.getTargetClass(invocation.getThis());
        Method method = invocation.getMethod();
        if (!cls.equals(invocation.getThis().getClass())) {
            logger.debug(invocation.getThis().getClass() + "-->" + cls);
            return invocation.proceed();
        }
        logger.debug(invocation.toString());
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cache = method.getAnnotation(Cache.class);
            logger.debug(
                    invocation.getThis().getClass().getName()
                            + "."
                            + method.getName()
                            + "-->@Cache");
            return cacheHandler.proceed(new CacheAopProxy(invocation), cache);
        } else {
            Method specificMethod =
                    AopUtils.getMostSpecificMethod(method, invocation.getThis().getClass());
            if (specificMethod.isAnnotationPresent(Cache.class)) {
                Cache cache = specificMethod.getAnnotation(Cache.class);
                logger.debug(
                        invocation.getThis().getClass().getName()
                                + "."
                                + specificMethod.getName()
                                + "-->@Cache");
                return cacheHandler.proceed(new CacheAopProxy(invocation), cache);
            }
        }
        return invocation.proceed();
    }
}

package com.github.jiahaowen.spring.assistant.component.cache.spring.interceptor;

import com.github.jiahaowen.spring.assistant.component.cache.CacheHandler;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.CacheDelete;
import com.github.jiahaowen.spring.assistant.component.cache.spring.autoconfigure.AutoloadCacheProperties;
import com.github.jiahaowen.spring.assistant.component.cache.spring.interceptor.aopproxy.DeleteCacheAopProxy;
import com.github.jiahaowen.spring.assistant.component.cache.util.AopUtil;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

/**
 * 对@CacheDelete 拦截注解
 *
 * @author jiahaowen
 */
public class CacheDeleteInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CacheDeleteInterceptor.class);

    private final CacheHandler cacheHandler;

    private final AutoloadCacheProperties config;

    public CacheDeleteInterceptor(CacheHandler cacheHandler, AutoloadCacheProperties config) {
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
        Object result = invocation.proceed();
        if (method.isAnnotationPresent(CacheDelete.class)) {
            CacheDelete cacheDelete = method.getAnnotation(CacheDelete.class);
            logger.debug(
                    invocation.getThis().getClass().getName()
                            + "."
                            + method.getName()
                            + "-->@CacheDelete");
            cacheHandler.deleteCache(new DeleteCacheAopProxy(invocation), cacheDelete, result);
        } else {
            Method specificMethod =
                    AopUtils.getMostSpecificMethod(method, invocation.getThis().getClass());
            if (specificMethod.isAnnotationPresent(CacheDelete.class)) {
                CacheDelete cacheDelete = specificMethod.getAnnotation(CacheDelete.class);
                logger.debug(
                        invocation.getThis().getClass().getName()
                                + "."
                                + specificMethod.getName()
                                + "-->@CacheDelete");
                cacheHandler.deleteCache(new DeleteCacheAopProxy(invocation), cacheDelete, result);
            }
        }
        return result;
    }
}

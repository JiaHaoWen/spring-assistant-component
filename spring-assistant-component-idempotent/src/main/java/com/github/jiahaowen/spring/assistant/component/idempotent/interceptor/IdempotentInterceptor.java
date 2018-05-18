package com.github.jiahaowen.spring.assistant.component.idempotent.interceptor;

import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.Idempotent;
import com.github.jiahaowen.spring.assistant.component.idempotent.data.IdempotentOperateUtil;
import com.github.jiahaowen.spring.assistant.component.idempotent.key.DefaultIdempotentKey;
import com.github.jiahaowen.spring.assistant.component.idempotent.key.DefaultReturnValue;
import com.github.jiahaowen.spring.assistant.component.idempotent.key.IdempotentKeyStrategy;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author jiahaowen
 */
@Component
public class IdempotentInterceptor implements MethodInterceptor {
    private static final Logger log = LoggerFactory.getLogger(IdempotentInterceptor.class);
    private IdempotentKeyStrategy distributeLockCacheKeyStrategy = new DefaultIdempotentKey();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(Idempotent.class)) {
            Idempotent idempotent = method.getAnnotation(Idempotent.class);
            final String key = distributeLockCacheKeyStrategy.key(invocation);
            if (!IdempotentOperateUtil.proceed(key, idempotent.expiredTime())) {
                return new DefaultReturnValue().value(idempotent);
            }
            try {
                return invocation.proceed();
            } catch (Throwable throwable) {
                log.warn(
                        "业务操作失败，设置的唯一标识需要清理。当前执行的方法为：method：{}，缓存key为：{}，错误异常为：",
                        method.getName(),
                        key,
                        throwable);
                IdempotentOperateUtil.callBack(key);
                throw throwable;
            }
        }
        return invocation.proceed();
    }
}

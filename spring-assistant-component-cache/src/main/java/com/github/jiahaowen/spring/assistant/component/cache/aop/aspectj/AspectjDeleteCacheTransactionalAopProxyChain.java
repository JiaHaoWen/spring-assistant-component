package com.github.jiahaowen.spring.assistant.component.cache.aop.aspectj;

import com.github.jiahaowen.spring.assistant.component.cache.aop.DeleteCacheTransactionalAopProxyChain;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 *
 * @author jiahaowen
 */
public class AspectjDeleteCacheTransactionalAopProxyChain implements
    DeleteCacheTransactionalAopProxyChain {

    private final ProceedingJoinPoint jp;

    public AspectjDeleteCacheTransactionalAopProxyChain(ProceedingJoinPoint jp) {
        this.jp=jp;

    }

    @Override
    public Object doProxyChain() throws Throwable {
        return jp.proceed();
    }

}

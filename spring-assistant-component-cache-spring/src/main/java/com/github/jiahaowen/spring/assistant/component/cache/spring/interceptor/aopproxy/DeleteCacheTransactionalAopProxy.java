package com.github.jiahaowen.spring.assistant.component.cache.spring.interceptor.aopproxy;

import com.github.jiahaowen.spring.assistant.component.cache.aop.DeleteCacheTransactionalAopProxyChain;
import org.aopalliance.intercept.MethodInvocation;

public class DeleteCacheTransactionalAopProxy implements DeleteCacheTransactionalAopProxyChain {

    private final MethodInvocation invocation;

    public DeleteCacheTransactionalAopProxy(MethodInvocation invocation) {
        this.invocation = invocation;
    }

    @Override
    public Object doProxyChain() throws Throwable {
        return invocation.proceed();
    }
}

package com.github.jiahaowen.spring.assistant.component.cache.aop;

/**
 *
 * @author jiahaowen
 */
public interface DeleteCacheTransactionalAopProxyChain {

    /**
     * 执行方法
     * @return 执行结果
     * @throws Throwable Throwable
     */
    Object doProxyChain() throws Throwable;
}

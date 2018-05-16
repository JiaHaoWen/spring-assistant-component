package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.splitter;

import java.lang.reflect.Method;

/**
 * 请求分流器 只做A、B分流
 *
 * @author jiahaowen
 * @version $Id: Splitter.java, v 0.1 16/11/18 下午5:25 jiahaowen Exp $
 */
public interface Splitter<T> {
    /**
     * 是否走B方案
     *
     * @param t
     * @return
     */
    boolean goBizB(Method method, T... t);

    /**
     * 是否需要Check
     *
     * @param t
     * @return
     */
    boolean doCheckBizB(Method method, T... t);

    /**
     * 是否为异步分流校验,默认为异步
     *
     * @param method
     * @param t
     * @return
     */
    boolean isAsynShuntAndCheck(Method method, T... t);
}

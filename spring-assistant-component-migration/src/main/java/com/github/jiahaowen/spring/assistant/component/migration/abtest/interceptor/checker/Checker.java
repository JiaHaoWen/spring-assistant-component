/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker;

/**
 * 对象比较器
 * @author jiahaowen
 * @version $Id: Check.java, v 0.1 16/11/18 下午5:25 jiahaowen Exp $
 */
public interface Checker<T> {

    /**
     * 返回不一致的字符串，
     * 若比对一致返回null、空字符串
     * @param t1
     * @param t2
     * @return
     */
    String doCheck(T t1, T t2);
}

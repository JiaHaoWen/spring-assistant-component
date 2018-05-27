package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import groovy.lang.Binding;

/**
 * 参数上下文持有器
 *
 * @author jiahaowen.jhw
 * @version $Id: BindingContextHolder.java, v 0.1 2016-12-01 下午8:22 jiahaowen.jhw Exp $
 */
public class BindingContextHolder {

    public static ThreadLocal<Binding> bindingThreadLocal = new ThreadLocal<Binding>();

    public static Binding getBinding() {
        return bindingThreadLocal.get();
    }

    public static void setBinding(Binding binding) {
        bindingThreadLocal.set(binding);
    }

    public static void clear() {
        bindingThreadLocal.remove();
    }
}

package com.github.jiahaowen.spring.assistant.component.cache.util;

import org.springframework.aop.framework.AopProxyUtils;

/** @author jiahaowen */
public class AopUtil {

    /**
     * @param target
     * @return
     */
    public static Class<?> getTargetClass(Object target) {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
        if (targetClass == null && target != null) {
            targetClass = target.getClass();
        }
        return targetClass;
    }
}

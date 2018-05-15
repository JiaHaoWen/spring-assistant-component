package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation;

import java.lang.reflect.Method;

/**
 * 用户<code>ABTestAspectSupport</code>使用，用于发现ABTest的操作属性，及其元信息。
 *
 * @author jiahaowen
 * @version $Id: ABTestOperationSource.java, v 0.1 16/11/18 下午5:40 jiahaowen Exp $
 */
public interface ABTestOperationSource {
    ABTestOperation getABTestOperation(Method method, Class<?> targetClass);
}

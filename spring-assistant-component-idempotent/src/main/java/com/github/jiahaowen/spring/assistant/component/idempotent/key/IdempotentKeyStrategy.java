package com.github.jiahaowen.spring.assistant.component.idempotent.key;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author jiahaowen
 * @date 2017/8/1 14:53
 */
public interface IdempotentKeyStrategy {
    String key(MethodInvocation invocation);
}

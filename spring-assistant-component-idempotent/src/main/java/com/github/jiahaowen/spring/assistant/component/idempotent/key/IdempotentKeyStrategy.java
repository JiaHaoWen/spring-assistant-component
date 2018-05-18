package com.github.jiahaowen.spring.assistant.component.idempotent.key;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author jiahaowen
 */
public interface IdempotentKeyStrategy {
    String key(MethodInvocation invocation);
}

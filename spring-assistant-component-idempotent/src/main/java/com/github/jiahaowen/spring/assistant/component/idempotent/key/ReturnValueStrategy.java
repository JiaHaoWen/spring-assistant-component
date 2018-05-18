package com.github.jiahaowen.spring.assistant.component.idempotent.key;

import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.Idempotent;

/**
 * @author jiahaowen
 */
public interface ReturnValueStrategy {
    Object value(Idempotent idempotent);
}

package com.github.jiahaowen.spring.assistant.component.idempotent.key;

import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.Idempotent;

/**
 * @author jiahaowen
 * @date 2017/9/12 19:44
 */
public interface ReturnValueStrategy {
    Object value(Idempotent idempotent);
}

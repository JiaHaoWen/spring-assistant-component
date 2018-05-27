package com.github.jiahaowen.spring.assistant.component.rule.model;

import java.util.List;

/**
 * @author jiahaowen.jhw
 * @version $Id: ReduceFunction.java, v 0.1 2016-12-07 下午6:58 jiahaowen.jhw Exp $
 */
public interface ReduceFunction<Resp extends RuleResponse> {
    Object invoke(List<Object> respList);

    String code();
}

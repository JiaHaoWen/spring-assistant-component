package com.github.jiahaowen.spring.assistant.component.rule.model;

/**
 * @author jiahaowen.jhw
 * @version $Id: RuleResponseGenerator.java, v 0.1 2016-12-07 下午6:53 jiahaowen.jhw Exp $
 */
public interface RuleResponseGenerator<Resp extends RuleResponse> {

    String description();

    Object generate();

    String code();
}

package com.github.jiahaowen.spring.assistant.component.rule.model;

/**
 * @author jiahaowen.jhw
 * @version $Id: RuleCondition.java, v 0.1 2016-12-07 下午6:53 jiahaowen.jhw Exp $
 */
public interface RuleCondition {

    String description();

    boolean condition();

    String code();
}

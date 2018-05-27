package com.github.jiahaowen.spring.assistant.component.rule.model;

/**
 * 规则节点
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleNode.java, v 0.1 2016-12-01 下午9:05 jiahaowen.jhw Exp $
 */
public interface RuleNode<Resp extends RuleResponse> {

    Object execute();

    boolean condition();

    NodeStructureSpec visit();

    String description();
}

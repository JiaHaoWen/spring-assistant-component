package com.github.jiahaowen.spring.assistant.component.rule.api;

/**
 * 规则引擎 装配定制构造器
 *
 * <p>RuleRunner runner = ruleRunnerBuilder.buildRuleRunner();
 *
 * <p>Object result = runner.execute("ruleName",Maps.newHashMap());
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleRunnerBuilder.java, v 0.1 2016-12-01 下午5:17 jiahaowen.jhw Exp $
 */
public interface RuleRunnerBuilder {

    /**
     * 构建一个规则执行器
     *
     * @return
     */
    RuleRunner buildRuleRunner();
}

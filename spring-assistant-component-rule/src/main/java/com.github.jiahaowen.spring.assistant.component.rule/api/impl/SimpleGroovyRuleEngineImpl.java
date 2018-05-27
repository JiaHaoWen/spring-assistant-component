package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.api.RuleEngine;
import com.github.jiahaowen.spring.assistant.component.rule.exception.RuleErrorCode;
import com.github.jiahaowen.spring.assistant.component.rule.exception.RuleException;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import groovy.lang.Binding;
import groovy.lang.Script;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * groovy规则引擎
 *
 * @author jiahaowen.jhw
 * @version $Id: SimpleGroovyRuleEngineImpl.java, v 0.1 2016-12-01 下午8:10 jiahaowen.jhw Exp $
 */
@Component
public class SimpleGroovyRuleEngineImpl implements RuleEngine {

    /** groovy编译器 */
    @Autowired private GroovyCompiler groovyCompiler;

    /**
     * 装载一个可执行groovy规则集并执行
     *
     * @param ruleScript 规则脚本
     * @param appContext 用户数据上下文
     * @param systemContext 系统上下文
     * @return 规则集
     */
    @Override
    public Object execute(
            RuleScript ruleScript,
            Map<String, Object> appContext,
            Map<String, Object> systemContext) {
        Class<Script> scriptClass = groovyCompiler.compile(ruleScript);

        try {
            Script script = scriptClass.newInstance();
            // 执行groovy脚本
            Binding binding = new Binding();
            setProperty(binding, appContext, systemContext);
            script.setBinding(binding);
            Object result = script.run();
            return result;

        } catch (InstantiationException e) {
            throw new RuleException(RuleErrorCode.RULE_GROOVY_INSTANTIATION_ERROR, e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuleException(RuleErrorCode.RULE_GROOVY_INSTANTIATION_ERROR, e.getMessage());
        }
    }

    /**
     * 执行属性设置
     *
     * @param binding
     * @param appContext
     * @param systemContext
     */
    private void setProperty(
            Binding binding, Map<String, Object> appContext, Map<String, Object> systemContext) {

        for (Map.Entry<String, Object> entry : systemContext.entrySet()) {
            binding.setProperty(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Object> entry : appContext.entrySet()) {
            binding.setProperty(entry.getKey(), entry.getValue());
        }
    }
}

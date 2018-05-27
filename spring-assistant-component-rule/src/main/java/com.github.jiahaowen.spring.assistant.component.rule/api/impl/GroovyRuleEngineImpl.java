package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.api.RuleEngine;
import com.github.jiahaowen.spring.assistant.component.rule.exception.RuleErrorCode;
import com.github.jiahaowen.spring.assistant.component.rule.exception.RuleException;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleNode;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.Script;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * groovy脚本执行引擎
 *
 * @author jiahaowen.jhw
 * @version $Id: GroovyRuleEngineImpl.java, v 0.1 2016-12-01 下午8:18 jiahaowen.jhw Exp $
 */
@Component
public class GroovyRuleEngineImpl implements RuleEngine {

    public static AtomicBoolean isOptimize = new AtomicBoolean(true);
    private static Map<String, RuleNode> scriptAndNodeCachedMap = Maps.newConcurrentMap();
    private static Map<Class, CustomMetaClassImpl> customMetaClassMap = Maps.newConcurrentMap();
    @Autowired private GroovyCompiler groovyCompiler;

    /**
     * 装载一个可执行目标规则集并执行
     *
     * @param script 规则脚本
     * @param appContext
     * @param systemContext @return 规则集
     */
    @Override
    public Object execute(
            RuleScript script, Map<String, Object> appContext, Map<String, Object> systemContext) {

        Class<Script> scriptClass = groovyCompiler.compile(script);
        Object executeResult = null;
        try {
            Script scriptInstance = scriptClass.newInstance();
            Binding binding;
            if (isOptimize.get()) {
                if (customMetaClassMap.get(scriptClass) == null) {
                    customMetaClassMap.put(scriptClass, new CustomMetaClassImpl(scriptClass));
                }
                scriptInstance.setMetaClass(customMetaClassMap.get(scriptClass));
                binding = new CustomBinding(systemContext, appContext);
            } else {
                binding = new Binding();
                setProperty(binding, appContext, systemContext);
            }

            scriptInstance.setBinding(binding);

            BindingContextHolder.setBinding(binding);

            if (scriptAndNodeCachedMap.get(script.getName()) != null) {
                RuleNode cachedNode = scriptAndNodeCachedMap.get(script.getName());
                binding.setVariable("__cachedRuleNode__", cachedNode);
            } else {
                binding.setVariable("__cachedRuleNode__", null);
            }

            Object ruleResult = scriptInstance.run();

            if (RuleNode.class.isInstance(ruleResult)) {
                RuleNode ruleNode = (RuleNode) ruleResult;
                if (ruleNode.condition()) {
                    executeResult = ruleNode.execute();
                }
            } else {
                executeResult = ruleResult;
            }

        } catch (InstantiationException e) {
            throw new RuleException(RuleErrorCode.RULE_GROOVY_INSTANTIATION_ERROR, e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuleException(RuleErrorCode.RULE_GROOVY_INSTANTIATION_ERROR, e.getMessage());
        } finally {
            BindingContextHolder.clear();
        }
        return executeResult;
    }

    private void setProperty(
            Binding binding, Map<String, Object> appContext, Map<String, Object> systemContext) {

        if (systemContext != null) {
            for (Map.Entry<String, Object> entry : systemContext.entrySet()) {
                binding.setProperty(entry.getKey(), entry.getValue());
            }
        }

        if (appContext != null) {
            for (Map.Entry<String, Object> entry : appContext.entrySet()) {
                binding.setProperty(entry.getKey(), entry.getValue());
            }
        }
    }
}

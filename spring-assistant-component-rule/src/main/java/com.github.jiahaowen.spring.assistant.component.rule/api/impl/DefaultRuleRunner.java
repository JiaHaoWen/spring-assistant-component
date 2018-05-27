package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.api.RuleContextLoader;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleEngine;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleRunner;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleScriptLoader;
import com.github.jiahaowen.spring.assistant.component.rule.exception.RuleErrorCode;
import com.github.jiahaowen.spring.assistant.component.rule.exception.RuleException;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import com.github.jiahaowen.spring.assistant.component.rule.util.RuleGlobalHolder;
import com.github.jiahaowen.spring.assistant.component.util.common.util.StringUtil;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 默认规则执行器
 *
 * @author jiahaowen.jhw
 * @version $Id: DefaultRuleRunner.java, v 0.1 2016-12-01 下午8:52 jiahaowen.jhw Exp $
 */
@Component
public class DefaultRuleRunner implements RuleRunner {

    private static final Logger logger = Logger.getLogger(DefaultRuleRunner.class);

    /** 规则脚本加载 */
    @Autowired private RuleScriptLoader ruleScriptLoader;

    /** 规则上下文加载 */
    @Autowired private RuleContextLoader ruleContextLoader;

    /** 规则引擎 */
    private Map<String, RuleEngine> ruleEngines;

    /**
     * 根据规则名，找到对应的规则脚本并执行
     *
     * @param ruleName 规则名字
     * @param context 规则数据上下文
     * @return
     */
    @Override
    public Object execute(String ruleName, Map<String, Object> context) {

        // 装载脚本
        RuleScript ruleScript = ruleScriptLoader.load(ruleName);

        if (ruleScript == null) {
            logger.error("can't found rulescript for rule:" + ruleName);
            return null;
        }

        return execute(ruleScript, context);
    }

    /**
     * 根据规则名，找到对应的规则脚本并执行 区别于execute，此方法内部会对异常进行捕获，避免异常影响调用流程。
     *
     * @param ruleName 规则名字
     * @param context 规则数据上下文
     * @return
     */
    @Override
    public Object safeExecute(String ruleName, Map<String, Object> context) {
        try {
            return execute(ruleName, context);
        } catch (Exception e) {
            logger.error("execute rule occurs error:" + ruleName);
            return null;
        }
    }

    /**
     * 根据RuleScript对象，执行规则
     *
     * @param ruleScript 规则脚本
     * @param context 规则数据上下文
     * @return
     */
    @Override
    public Object execute(RuleScript ruleScript, Map<String, Object> context) {
        if (ruleScript == null) {
            throw new RuleException(
                    RuleErrorCode.RULE_SCRIPT_NOT_FOUND, "rulename:" + ruleScript.getName());
        }

        if (StringUtil.isBlank(ruleScript.getNameSpace())) {
            throw new RuleException(
                    RuleErrorCode.UNKNOWN_NAMESPACE_RULE, "rulename:" + ruleScript.getName());
        }

        RuleGlobalHolder.addNameSpace(ruleScript.getNameSpace());

        // 准备系统上下文
        Map<String, Object> systemContext = ruleContextLoader.load(ruleScript, context);

        // 拼接系统上下文\用户数据上下文,规则里调用规则可能会用到
        addRuleRunnerToSystemContext(systemContext);

        // 装载与脚本匹配的规则引擎
        RuleEngine ruleEngine = matchRuleEngine(ruleScript);
        if (ruleEngine == null) {
            throw new RuleException(RuleErrorCode.RULE_ENGINE_NOT_MATCHE, ruleScript.toString());
        }

        try {
            // 执行规则
            Object result = ruleEngine.execute(ruleScript, context, systemContext);
            return result;
        } catch (Exception e) {
            logger.error(
                    "execute rule " + ruleScript.getName() + " occurs error. context=" + context,
                    e);
            throw new RuleException(e.getMessage(), e);
        }
    }

    /**
     * 系统执行上下文
     *
     * @param systemContext
     */
    private void addRuleRunnerToSystemContext(Map<String, Object> systemContext) {
        systemContext.put("ruleRunner", this);
    }

    /**
     * 根据规则类型匹配具体的规则引擎
     *
     * @param ruleScript
     * @return
     */
    private RuleEngine matchRuleEngine(RuleScript ruleScript) {
        return ruleEngines.get(ruleScript.getScriptLanguage());
    }

    /**
     * Setter method for property ruleScriptLoader.
     *
     * @param ruleScriptLoader value to be assigned to property ruleScriptLoader
     */
    public void setRuleScriptLoader(RuleScriptLoader ruleScriptLoader) {
        this.ruleScriptLoader = ruleScriptLoader;
    }

    /**
     * Setter method for property ruleContextLoader.
     *
     * @param ruleContextLoader value to be assigned to property ruleContextLoader
     */
    public void setRuleContextLoader(RuleContextLoader ruleContextLoader) {
        this.ruleContextLoader = ruleContextLoader;
    }

    /**
     * Setter method for property ruleEngines.
     *
     * @param ruleEngines value to be assigned to property ruleEngines
     */
    public void setRuleEngines(Map<String, RuleEngine> ruleEngines) {
        this.ruleEngines = ruleEngines;
    }
}

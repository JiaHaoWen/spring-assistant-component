package com.github.jiahaowen.spring.assistant.component.rule.api;


import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import java.util.Map;

/**
 * 规则引擎
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleEngine.java, v 0.1 2016-12-01 下午5:09 jiahaowen.jhw Exp $
 */
public interface RuleEngine {

    /**
     * 装载一个可执行目标规则集并执行
     *
     * @param script 规则脚本
     * @return 规则集
     */
    Object execute(
            RuleScript script, Map<String, Object> appContext, Map<String, Object> systemContext);
}

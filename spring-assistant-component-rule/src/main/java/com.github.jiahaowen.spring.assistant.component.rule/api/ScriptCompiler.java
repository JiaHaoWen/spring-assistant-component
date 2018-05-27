package com.github.jiahaowen.spring.assistant.component.rule.api;


import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;

/**
 * 脚本编译器
 *
 * @author jiahaowen.jhw
 * @version $Id: ScriptCompiler.java, v 0.1 2016-12-01 下午5:14 jiahaowen.jhw Exp $
 */
public interface ScriptCompiler<T> {

    /**
     * 编译一个脚本
     *
     * @param ruleScript
     * @return
     */
    T compile(RuleScript ruleScript);
}

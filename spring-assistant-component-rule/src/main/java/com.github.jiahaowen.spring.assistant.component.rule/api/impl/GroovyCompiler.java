package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.api.ScriptCompiler;
import com.github.jiahaowen.spring.assistant.component.rule.exception.RuleErrorCode;
import com.github.jiahaowen.spring.assistant.component.rule.exception.RuleException;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import com.github.jiahaowen.spring.assistant.component.util.common.util.StringUtil;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * groovy编译器
 *
 * @author jiahaowen.jhw
 * @version $Id: GroovyCompiler.java, v 0.1 2016-12-01 下午5:44 jiahaowen.jhw Exp $
 */
@Component
public class GroovyCompiler implements ScriptCompiler<Class<Script>> {

    private static final Logger logger = Logger.getLogger(GroovyCompiler.class);

    /**
     * 编译一个groovy脚本
     *
     * @param ruleScript
     * @return
     */
    @Override
    public Class<Script> compile(RuleScript ruleScript) {
        GroovyClassLoader groovyLoader =
                CustomGroovyClassLoaderFactory.newClassLoaderWithImplictImport();
        try {
            // 每次真正触发groovy编译，打个日志
            logger.warn("start compile rulescript:" + ruleScript.getName());

            if (StringUtil.isEmpty(ruleScript.getScript())) { // 为空不需要编译
                return null;
            }

            Class<Script> scriptClass =
                    (Class<Script>) groovyLoader.parseClass(ruleScript.getScript());

            logger.warn("compile end.");

            return scriptClass;
        } catch (Exception e) {
            throw new RuleException(RuleErrorCode.RULE_COMPILE_ERROR, ruleScript.toString(), e);
        }
    }
}

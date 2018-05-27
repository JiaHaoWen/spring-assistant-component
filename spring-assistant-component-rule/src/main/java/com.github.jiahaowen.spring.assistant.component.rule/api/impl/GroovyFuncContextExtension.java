package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.api.ContextExtension;
import com.github.jiahaowen.spring.assistant.component.rule.database.RuleScriptSimpleDO;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import com.github.jiahaowen.spring.assistant.component.rule.model.ScriptLanguage;
import com.github.jiahaowen.spring.assistant.component.rule.repository.convertor.RuleScriptConverter;
import com.github.jiahaowen.spring.assistant.component.rule.repository.repository.ScriptDataService;
import com.google.common.collect.Maps;
import groovy.lang.Script;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * groovy函数库上下文扩展点
 *
 * @author jiahaowen.jhw
 * @version $Id: GroovyFuncContextExtension.java, v 0.1 2016-12-01 下午8:36 jiahaowen.jhw Exp $
 */
@Component
public class GroovyFuncContextExtension implements ContextExtension {

    private static final Logger logger = Logger.getLogger(GroovyFuncContextExtension.class);

    @Autowired protected ScriptDataService scriptDataService;

    @Autowired protected GroovyCompiler groovyCompiler;

    @Override
    public Map<String, Object> extend(RuleScript ruleScript) {
        return extend(ruleScript.getNameSpace());
    }

    public Map<String, Object> extend(String nameSpace) {
        List<RuleScriptSimpleDO> functionScriptDOs =
                scriptDataService.queryFunctionScriptsByLanguage(
                        ScriptLanguage.groovy.name(), nameSpace);
        Map<String, Object> context = Maps.newHashMap();
        if (functionScriptDOs == null || functionScriptDOs.isEmpty()) {
            return context;
        }
        List<RuleScript> functionScripts = new ArrayList<RuleScript>();
        for (RuleScriptSimpleDO ruleScriptDO : functionScriptDOs) {
            RuleScript ruleScript = RuleScriptConverter.convert(ruleScriptDO);
            if (ruleScript == null) {
                continue;
            }
            functionScripts.add(ruleScript);
            Object func = getFunctionObject(ruleScript);
            if (func != null) {
                context.put(ruleScript.getName(), func);
            }
        }

        return context;
    }

    public Object getFunctionObject(RuleScript ruleScript) {
        Class<Script> scriptClass = groovyCompiler.compile(ruleScript);

        try {
            return scriptClass.newInstance().run();
        } catch (InstantiationException e) {
            logger.error("函数初始化失败", e);
            return null;
            // throw new RuleException(RuleErrorCode.RULE_GROOVY_INSTANTIATION_ERROR,
            // e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("函数初始化失败", e);
            return null;
            // throw new RuleException(RuleErrorCode.RULE_GROOVY_INSTANTIATION_ERROR,
            // e.getMessage());
        } catch (Exception e) {
            logger.error("函数初始化失败", e);
            return null;
        }
    }
}

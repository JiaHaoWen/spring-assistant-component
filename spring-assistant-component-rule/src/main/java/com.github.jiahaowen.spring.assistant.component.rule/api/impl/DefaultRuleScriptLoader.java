package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.api.RuleScriptLoader;
import com.github.jiahaowen.spring.assistant.component.rule.database.RuleScriptSimpleDO;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import com.github.jiahaowen.spring.assistant.component.rule.repository.convertor.RuleScriptConverter;
import com.github.jiahaowen.spring.assistant.component.rule.repository.repository.ScriptDataService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 默认规则脚本加载
 *
 * @author jiahaowen.jhw
 * @version $Id: DefaultRuleScriptLoader.java, v 0.1 2016-12-02 上午10:38 jiahaowen.jhw Exp $
 */
@Component
public class DefaultRuleScriptLoader implements RuleScriptLoader {

    /** 规则数据服务 */
    @Autowired protected ScriptDataService scriptDataService;

    /**
     * 预加载某一些业务类型的规则脚本
     *
     * <p>一般在应用启动是做，避免在运行时远程访问请求规则脚本
     *
     * @param businessNames
     */
    @Override
    public void preLoad(String[] businessNames) {}

    /**
     * 加载一个规则脚本
     *
     * @param name 规则名字
     * @return
     */
    @Override
    public RuleScript load(String name) {
        RuleScriptSimpleDO ruleScriptDO = scriptDataService.queryRuleScripByName(name, false);
        return RuleScriptConverter.convert(ruleScriptDO);
    }

    /**
     * 加载多个规则脚本
     *
     * @param names 规则名字
     * @return
     */
    @Override
    public List<RuleScript> load(List<String> names) {
        List<RuleScriptSimpleDO> ruleScriptDOs =
                scriptDataService.queryRuleScripByNames(names, false);
        List<RuleScript> ruleScripts = new ArrayList<RuleScript>(ruleScriptDOs.size());
        for (RuleScriptSimpleDO ruleScriptDO : ruleScriptDOs) {
            RuleScript ruleScript = RuleScriptConverter.convert(ruleScriptDO);
            if (ruleScript != null) {
                ruleScripts.add(ruleScript);
            }
        }
        return ruleScripts;
    }

    /**
     * Setter method for property scriptDataService.
     *
     * @param scriptDataService value to be assigned to property scriptDataService
     */
    public void setScriptDataService(ScriptDataService scriptDataService) {
        this.scriptDataService = scriptDataService;
    }
}

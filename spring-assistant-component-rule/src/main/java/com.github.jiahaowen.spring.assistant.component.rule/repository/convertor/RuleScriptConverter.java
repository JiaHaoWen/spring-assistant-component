package com.github.jiahaowen.spring.assistant.component.rule.repository.convertor;

import com.github.jiahaowen.spring.assistant.component.rule.database.RuleScriptSimpleDO;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import com.github.jiahaowen.spring.assistant.component.util.common.util.StringUtil;

/**
 * 脚本转换器
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleScriptConverter.java, v 0.1 2016-12-01 下午8:44 jiahaowen.jhw Exp $
 */
public class RuleScriptConverter {

    /**
     * RuleScriptDO->RuleScript
     *
     * @param ruleScriptDO
     * @return
     */
    public static RuleScript convert(RuleScriptSimpleDO ruleScriptDO) {
        if (ruleScriptDO == null) {
            return null;
        }

        RuleScript ruleScript = new RuleScript();
        ruleScript.setName(ruleScriptDO.getRuleName());
        ruleScript.setScriptLanguage(ruleScriptDO.getLanguageType());
        ruleScript.setScript(ruleScriptDO.getRuleBody());
        ruleScript.setNameSpace(ruleScriptDO.getNameSpace());

        if (StringUtil.isBlank(ruleScript.getScript())) {
            return null;
        }
        return ruleScript;
    }
}

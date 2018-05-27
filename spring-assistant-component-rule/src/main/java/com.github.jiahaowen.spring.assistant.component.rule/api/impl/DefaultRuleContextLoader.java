package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.api.ContextExtension;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleContextLoader;
import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 默认规则上下文加载器
 *
 * @author jiahaowen.jhw
 * @version $Id: DefaultRuleContextLoader.java, v 0.1 2016-12-02 上午10:44 jiahaowen.jhw Exp $
 */
@Component
public class DefaultRuleContextLoader implements RuleContextLoader {

    /** 语言级别的上下文扩展点 */
    private Map<String, List<ContextExtension>> languageContextExtensions;

    /** 规则级别的上下文扩展点 */
    private Map<String, List<ContextExtension>> ruleContextExtensions;

    /**
     * 装载规则执行上下文<pre>
     * 获取系统上下文并和应用上下文合并
     * <p/>
     * <p/>
     * @param ruleScript
     * @param context 应用上下文
     * @return 规则执行上下文
     */
    @Override
    public Map<String, Object> load(RuleScript ruleScript, Map<String, Object> context) {
        Map<String, Object> ruleContext = Maps.newHashMap();
        attachLanguageContext(ruleScript, ruleContext);
        attachSpecifiedRuleContext(ruleScript, ruleContext);
        return ruleContext;
    }

    private void attachLanguageContext(RuleScript ruleScript, Map<String, Object> ruleContext) {
        if (languageContextExtensions == null
                || languageContextExtensions.get(ruleScript.getScriptLanguage()) == null) {
            return;
        }
        for (ContextExtension contextExtension :
                languageContextExtensions.get(ruleScript.getScriptLanguage())) {
            Map<String, Object> context = contextExtension.extend(ruleScript);
            ruleContext.putAll(context);
        }
    }

    private void attachSpecifiedRuleContext(
            RuleScript ruleScript, Map<String, Object> ruleContext) {
        if (ruleContextExtensions == null
                || ruleContextExtensions.get(ruleScript.getName()) == null) {
            return;
        }
        for (ContextExtension contextExtension : ruleContextExtensions.get(ruleScript.getName())) {
            Map<String, Object> context = contextExtension.extend(ruleScript);
            ruleContext.putAll(context);
        }
    }

    /**
     * Setter method for property languageContextExtensions.
     *
     * @param languageContextExtensions value to be assigned to property languageContextExtensions
     */
    public void setLanguageContextExtensions(
            Map<String, List<ContextExtension>> languageContextExtensions) {
        this.languageContextExtensions = languageContextExtensions;
    }

    /**
     * Setter method for property ruleContextExtensions.
     *
     * @param ruleContextExtensions value to be assigned to property ruleContextExtensions
     */
    public void setRuleContextExtensions(
            Map<String, List<ContextExtension>> ruleContextExtensions) {
        this.ruleContextExtensions = ruleContextExtensions;
    }
}

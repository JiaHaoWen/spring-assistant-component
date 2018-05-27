package com.github.jiahaowen.spring.assistant.component.rule.api;


import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import java.util.Map;

/**
 * 规则执行上下文装载器
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleContextLoader.java, v 0.1 2016-12-01 下午5:17 jiahaowen.jhw Exp $
 */
public interface RuleContextLoader {

    /**
     * 装载规则执行上下文<pre>
     * 获取系统上下文并和应用上下文合并
     *
     * @param context 应用上下文
     * @return 规则执行上下文
     */
    Map<String, Object> load(RuleScript ruleScript, Map<String, Object> context);
}

package com.github.jiahaowen.spring.assistant.component.rule.api;


import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import java.util.Map;

/**
 * 规则执行上下文扩展点
 *
 * @author jiahaowen.jhw
 * @version $Id: ContextExtension.java, v 0.1 2016-12-01 下午8:35 jiahaowen.jhw Exp $
 */
public interface ContextExtension {

    Map<String, Object> extend(RuleScript ruleScript);
}

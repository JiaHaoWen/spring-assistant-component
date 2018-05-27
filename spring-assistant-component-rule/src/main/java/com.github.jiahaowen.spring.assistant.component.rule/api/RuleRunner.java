package com.github.jiahaowen.spring.assistant.component.rule.api;


import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import java.util.Map;

/**
 * 规则执行器,外部系统规则调用入口
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleRunner.java, v 0.1 2016-12-01 下午5:11 jiahaowen.jhw Exp $
 */
public interface RuleRunner {

    /**
     * 根据规则名，找到对应的规则脚本并执行
     *
     * @param ruleName 规则名字
     * @param context 规则数据上下文
     * @return
     */
    Object execute(String ruleName, Map<String, Object> context);

    /**
     * 根据规则名，找到对应的规则脚本并执行 区别于execute，此方法内部会对异常进行捕获，避免异常影响调用流程。
     *
     * @param ruleName 规则名字
     * @param context 规则数据上下文
     * @return
     */
    Object safeExecute(String ruleName, Map<String, Object> context);

    /**
     * 根据RuleScript对象，执行规则
     *
     * @param ruleScript 规则脚本
     * @param context 规则数据上下文
     * @return
     */
    Object execute(RuleScript ruleScript, Map<String, Object> context);
}

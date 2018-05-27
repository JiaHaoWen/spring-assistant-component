package com.github.jiahaowen.spring.assistant.component.rule.exception;

/**
 * 规则错误码
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleErrorCode.java, v 0.1 2016-12-01 下午5:48 jiahaowen.jhw Exp $
 */
public enum RuleErrorCode {
    RULE_SCRIPT_NOT_FOUND("规则脚本无法找到"),
    RULE_ENGINE_NOT_MATCHE("无法匹配对应的规则引擎"),
    RULE_COMPILE_ERROR("规则编译失败"),
    RULE_GENERAL_ERROR("系统失败"),
    RULE_GROOVY_INSTANTIATION_ERROR("groovy类实例初始化错误"),
    UNKNOWN_NAMESPACE_RULE("未知应用空间的规则");

    private String errorText;

    private RuleErrorCode(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }
}

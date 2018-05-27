package com.github.jiahaowen.spring.assistant.component.rule.exception;

/**
 * 规则运行时异常
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleException.java, v 0.1 2016-12-01 下午5:47 jiahaowen.jhw Exp $
 */
public class RuleException extends RuntimeException {

    private RuleErrorCode ruleErrorCode;

    private String msg;

    public RuleException(RuleErrorCode ruleErrorCode, String msg) {
        this(ruleErrorCode, msg, null);
    }

    public RuleException(RuleErrorCode ruleErrorCode, String msg, Throwable th) {
        super(ruleErrorCode.toString(), th);
        this.ruleErrorCode = ruleErrorCode;
        this.msg = msg;
    }

    public RuleException(RuleErrorCode ruleErrorCode) {
        this(ruleErrorCode, null, null);
    }

    public RuleException(String msg, Throwable th) {
        this(RuleErrorCode.RULE_GENERAL_ERROR, msg, th);
    }

    public String toString() {
        return "ErrorCode: "
                + ruleErrorCode.name()
                + " Error text:"
                + ruleErrorCode.getErrorText()
                + " Msg:"
                + msg;
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}

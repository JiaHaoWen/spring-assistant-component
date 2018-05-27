package com.github.jiahaowen.spring.assistant.component.rule.exception;

/**
 * @author jiahaowen.jhw
 * @version $Id: FuncNotFoundException.java, v 0.1 2016-12-01 下午9:13 jiahaowen.jhw Exp $
 */
public class FuncNotFoundException extends RuntimeException {

    public FuncNotFoundException(String message) {
        super(message);
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}

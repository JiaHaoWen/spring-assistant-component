package com.github.jiahaowen.spring.assistant.component.util.diff.exception;

/**
 * @author jiahaowen.jhw
 * @version $Id: DifferentiationFailedException.java, v 0.1 2016-10-30 下午9:58 jiahaowen.jhw Exp $
 */
public class DifferentiationFailedException extends DiffException {
    private static final long serialVersionUID = 1L;

    public DifferentiationFailedException() {}

    public DifferentiationFailedException(String msg) {
        super(msg);
    }
}

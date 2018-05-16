package com.github.jiahaowen.spring.assistant.component.util.diff.exception;

/**
 * @author jiahaowen.jhw
 * @version $Id: PatchFailedException.java, v 0.1 2016-10-30 下午9:55 jiahaowen.jhw Exp $
 */
public class PatchFailedException extends DiffException {

    private static final long serialVersionUID = 1L;

    public PatchFailedException() {}

    public PatchFailedException(String msg) {
        super(msg);
    }
}

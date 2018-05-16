package com.github.jiahaowen.spring.assistant.component.util.diff.exception;

import java.lang.reflect.InvocationTargetException;

/**
 * @author jiahaowen.jhw
 * @version $Id: UnreadablePropertyException.java, v 0.1 2016-10-30 下午9:28 jiahaowen.jhw Exp $
 */
public class UnreadablePropertyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnreadablePropertyException(
            final String path, final InvocationTargetException exception) {
        super("路径" + path + "下,由于原因" + exception.getCause() + "无法获得对象的可读属性");
    }
}

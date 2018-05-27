package com.github.jiahaowen.spring.assistant.component.rule.util;

import groovy.inspect.swingui.AstNodeToScriptVisitor;
import groovy.lang.Closure;

import java.io.StringWriter;

/**
 * @author jiahaowen.jhw
 * @version $Id: GroovyClosureUtil.java, v 0.1 2016-12-07 下午6:55 jiahaowen.jhw Exp $
 */
public class GroovyClosureUtil {

    public static String getClosureStatementCode(Closure closure) {
        StringWriter writer = new StringWriter();
        closure.getMetaClass()
                .getClassNode()
                .getDeclaredMethods("doCall")
                .get(0)
                .getCode()
                .visit(new AstNodeToScriptVisitor(writer));
        return writer.toString();
    }
}

package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import groovy.lang.Binding;

import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 变量绑定
 *
 * @author jiahaowen.jhw
 * @version $Id: CustomBinding.java, v 0.1 2016-12-01 下午9:11 jiahaowen.jhw Exp $
 */
@Component
public class CustomBinding extends Binding {

    private Map<String, Object> sysContext = null;
    private Map<String, Object> usrContext = null;

    public CustomBinding(Map<String, Object> sysContext, Map<String, Object> usrContext) {
        this.sysContext = sysContext;
        this.usrContext = usrContext;
    }

    @Override
    public Object getVariable(String name) {

        if (sysContext != null) {
            if (sysContext.containsKey(name)) return sysContext.get(name);
        }

        if (usrContext != null) {
            if (usrContext.containsKey(name)) return usrContext.get(name);
        }

        return super.getVariable(name); // null
    }

    @Override
    public boolean hasVariable(String name) {
        if (sysContext != null && sysContext.containsKey(name)) return true;

        if (usrContext != null && usrContext.containsKey(name)) return true;

        return super.hasVariable(name);
    }
}

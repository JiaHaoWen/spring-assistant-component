package com.github.jiahaowen.spring.assistant.component.rule.model;

/**
 * 脚本类型
 *
 * @author jiahaowen.jhw
 * @version $Id: ScriptLanguage.java, v 0.1 2016-12-01 下午8:39 jiahaowen.jhw Exp $
 */
public enum ScriptLanguage {
    groovy,
    simpleGroovy,
    qlexpress;

    public boolean isMatch(String language) {
        return this.name().equalsIgnoreCase(language);
    }
}

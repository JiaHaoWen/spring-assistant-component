package com.github.jiahaowen.spring.assistant.component.rule.model;

/**
 * 规则脚本状态
 *
 * @author jiahaowen.jhw
 * @version $Id: ScriptStatus.java, v 0.1 2016-12-02 下午2:15 jiahaowen.jhw Exp $
 */
public enum ScriptStatus {
    DRAFT("01", "编辑中"),
    GRAY_PUBLISH("02", "灰度"),
    PUBLISHED("03", "正式");

    private String code;

    private String desc;

    ScriptStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

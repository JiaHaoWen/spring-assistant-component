package com.github.jiahaowen.spring.assistant.component.rule.model;

/**
 * 规则名称空间
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleNameSpace.java, v 0.1 2016-12-01 下午8:57 jiahaowen.jhw Exp $
 */
public enum RuleNameSpace {
    ArRule("合约相关规则"),

    PdRule("产品相关规则");

    private String desc;

    private RuleNameSpace(String desc) {
        this.desc = desc;
    }

    public static RuleNameSpace getNameSpaceByName(String name) {
        for (RuleNameSpace nameSpace : RuleNameSpace.values()) {
            if (nameSpace.name().equalsIgnoreCase(name)) {
                return nameSpace;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

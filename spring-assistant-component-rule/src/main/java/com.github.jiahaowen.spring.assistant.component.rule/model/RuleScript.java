package com.github.jiahaowen.spring.assistant.component.rule.model;

/**
 * 规则脚本
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleScript.java, v 0.1 2016-12-01 下午5:06 jiahaowen.jhw Exp $
 */
public class RuleScript {

    /** 脚本名称 */
    private String name;

    /** 脚本语言 */
    private String scriptLanguage;

    /** 名称空间 */
    private String nameSpace;

    /** 脚本类型（如函数、规则） */
    private String type;

    /** 脚本内容 */
    private String script;

    /**
     * Getter method for property name.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property name.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property scriptLanguage.
     *
     * @return property value of scriptLanguage
     */
    public String getScriptLanguage() {
        return scriptLanguage;
    }

    /**
     * Setter method for property scriptLanguage.
     *
     * @param scriptLanguage value to be assigned to property scriptLanguage
     */
    public void setScriptLanguage(String scriptLanguage) {
        this.scriptLanguage = scriptLanguage;
    }

    /**
     * Getter method for property nameSpace.
     *
     * @return property value of nameSpace
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * Setter method for property nameSpace.
     *
     * @param nameSpace value to be assigned to property nameSpace
     */
    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    /**
     * Getter method for property type.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property type.
     *
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property script.
     *
     * @return property value of script
     */
    public String getScript() {
        return script;
    }

    /**
     * Setter method for property script.
     *
     * @param script value to be assigned to property script
     */
    public void setScript(String script) {
        this.script = script;
    }
}

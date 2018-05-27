package com.github.jiahaowen.spring.assistant.component.rule.database;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RuleScriptSimpleDO {

    // ========== properties ==========

    private long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String ruleName;

    private String ruleDesc;

    private String ruleBody;

    private String languageType;

    private String status;

    private String lastModer;

    private String moderTp;

    private String nameSpace;

    // ========== getters and setters ==========

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Setter method for property <tt>gmtCreate</tt>.
     *
     * @param gmtCreate value to be assigned to property gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Setter method for property <tt>gmtModified</tt>.
     *
     * @param gmtModified value to be assigned to property gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * Getter method for property <tt>ruleName</tt>.
     *
     * @return property value of ruleName
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * Setter method for property <tt>ruleName</tt>.
     *
     * @param ruleName value to be assigned to property ruleName
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * Getter method for property <tt>ruleDesc</tt>.
     *
     * @return property value of ruleDesc
     */
    public String getRuleDesc() {
        return ruleDesc;
    }

    /**
     * Setter method for property <tt>ruleDesc</tt>.
     *
     * @param ruleDesc value to be assigned to property ruleDesc
     */
    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    /**
     * Getter method for property <tt>ruleBody</tt>.
     *
     * @return property value of ruleBody
     */
    public String getRuleBody() {
        return ruleBody;
    }

    /**
     * Setter method for property <tt>ruleBody</tt>.
     *
     * @param ruleBody value to be assigned to property ruleBody
     */
    public void setRuleBody(String ruleBody) {
        this.ruleBody = ruleBody;
    }

    /**
     * Getter method for property <tt>languageType</tt>.
     *
     * @return property value of languageType
     */
    public String getLanguageType() {
        return languageType;
    }

    /**
     * Setter method for property <tt>languageType</tt>.
     *
     * @param languageType value to be assigned to property languageType
     */
    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>lastModer</tt>.
     *
     * @return property value of lastModer
     */
    public String getLastModer() {
        return lastModer;
    }

    /**
     * Setter method for property <tt>lastModer</tt>.
     *
     * @param lastModer value to be assigned to property lastModer
     */
    public void setLastModer(String lastModer) {
        this.lastModer = lastModer;
    }

    /**
     * Getter method for property <tt>moderTp</tt>.
     *
     * @return property value of moderTp
     */
    public String getModerTp() {
        return moderTp;
    }

    /**
     * Setter method for property <tt>moderTp</tt>.
     *
     * @param moderTp value to be assigned to property moderTp
     */
    public void setModerTp(String moderTp) {
        this.moderTp = moderTp;
    }

    /**
     * Getter method for property <tt>nameSpace</tt>.
     *
     * @return property value of nameSpace
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * Setter method for property <tt>nameSpace</tt>.
     *
     * @param nameSpace value to be assigned to property nameSpace
     */
    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    /** @see Object#toString() */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

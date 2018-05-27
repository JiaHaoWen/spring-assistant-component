package com.github.jiahaowen.spring.assistant.component.rule.repository.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jiahaowen.jhw
 * @version $Id: RuleScriptDO.java, v 0.1 2016-12-01 下午8:41 jiahaowen.jhw Exp $
 */
public class RuleScriptDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String nameSpace;

    private String business;

    private String writeMode;

    private String scriptLanguage;

    private String scriptType;

    private String scriptEditing;

    private String scriptStatus;

    private String grayMachineList;

    private String scriptReleased;

    private Date gmtCreate;

    private Date gmtModified;

    private String description;

    /**
     * Getter method for property description.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property description.
     *
     * @param description value to be assigned to property description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for property gmtModified.
     *
     * @return property value of gmtModified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Setter method for property gmtModified.
     *
     * @param gmtModified value to be assigned to property gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * Getter method for property id.
     *
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property id.
     *
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * Getter method for property business.
     *
     * @return property value of business
     */
    public String getBusiness() {
        return business;
    }

    /**
     * Setter method for property business.
     *
     * @param business value to be assigned to property business
     */
    public void setBusiness(String business) {
        this.business = business;
    }

    /**
     * Getter method for property writeMode.
     *
     * @return property value of writeMode
     */
    public String getWriteMode() {
        return writeMode;
    }

    /**
     * Setter method for property writeMode.
     *
     * @param writeMode value to be assigned to property writeMode
     */
    public void setWriteMode(String writeMode) {
        this.writeMode = writeMode;
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
     * Getter method for property scriptType.
     *
     * @return property value of scriptType
     */
    public String getScriptType() {
        return scriptType;
    }

    /**
     * Setter method for property scriptType.
     *
     * @param scriptType value to be assigned to property scriptType
     */
    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    /**
     * Getter method for property scriptEditing.
     *
     * @return property value of scriptEditing
     */
    public String getScriptEditing() {
        return scriptEditing;
    }

    /**
     * Setter method for property scriptEditing.
     *
     * @param scriptEditing value to be assigned to property scriptEditing
     */
    public void setScriptEditing(String scriptEditing) {
        this.scriptEditing = scriptEditing;
    }

    /**
     * Getter method for property scriptStatus.
     *
     * @return property value of scriptStatus
     */
    public String getScriptStatus() {
        return scriptStatus;
    }

    /**
     * Setter method for property scriptStatus.
     *
     * @param scriptStatus value to be assigned to property scriptStatus
     */
    public void setScriptStatus(String scriptStatus) {
        this.scriptStatus = scriptStatus;
    }

    /**
     * Getter method for property grayMachineList.
     *
     * @return property value of grayMachineList
     */
    public String getGrayMachineList() {
        return grayMachineList;
    }

    /**
     * Setter method for property grayMachineList.
     *
     * @param grayMachineList value to be assigned to property grayMachineList
     */
    public void setGrayMachineList(String grayMachineList) {
        this.grayMachineList = grayMachineList;
    }

    /**
     * Getter method for property scriptReleased.
     *
     * @return property value of scriptReleased
     */
    public String getScriptReleased() {
        return scriptReleased;
    }

    /**
     * Setter method for property scriptReleased.
     *
     * @param scriptReleased value to be assigned to property scriptReleased
     */
    public void setScriptReleased(String scriptReleased) {
        this.scriptReleased = scriptReleased;
    }

    /**
     * Getter method for property gmtCreate.
     *
     * @return property value of gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Setter method for property gmtCreate.
     *
     * @param gmtCreate value to be assigned to property gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}

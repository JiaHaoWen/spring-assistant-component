package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums;

import com.github.jiahaowen.spring.assistant.component.migration.common.util.ObjectUtil;

/**
 * 校验模式枚举
 *
 * @author jiahaowen.jhw
 * @version $Id: CheckModeEnum.java, v 0.1 2017-02-08 下午2:55 jiahaowen.jhw Exp $
 */
public enum CheckModeEnum {
    ASYNC(0, "ASYNC", "异步校验"),

    SYNC(1, "SYNC", "同步校验");

    private int code;

    private String englishName;

    private String desc;

    CheckModeEnum(int code, String englishName, String desc) {
        this.code = code;
        this.englishName = englishName;
        this.desc = desc;
    }

    public static CheckModeEnum getEnumByCode(Integer code) {
        for (CheckModeEnum checkModeEnum : CheckModeEnum.values()) {
            if (ObjectUtil.equals(checkModeEnum.getCode(), code)) {
                return checkModeEnum;
            }
        }
        return null;
    }

    /**
     * Getter method for property code.
     *
     * @return property value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Setter method for property code.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Getter method for property englishName.
     *
     * @return property value of englishName
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * Setter method for property englishName.
     *
     * @param englishName value to be assigned to property englishName
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    /**
     * Getter method for property desc.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property desc.
     *
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums;

import com.github.jiahaowen.spring.assistant.component.util.common.util.ObjectUtil;

/**
 * 拦截模式枚举
 *
 * @author jiahaowen.jhw
 * @version $Id: InterceptModeEnum.java, v 0.1 2017-02-04 下午4:28 jiahaowen.jhw Exp $
 */
public enum InterceptModeEnum {
    JVM(0, "JVM", "JVM内部方法拦截"),

    RPC(1, "RPC", "跨系统方法拦截");

    private int code;

    private String englishName;

    private String desc;

    InterceptModeEnum(int code, String englishName, String desc) {
        this.code = code;
        this.englishName = englishName;
        this.desc = desc;
    }

    public static InterceptModeEnum getEnumByCode(Integer code) {
        for (InterceptModeEnum interceptModeEnum : InterceptModeEnum.values()) {
            if (ObjectUtil.equals(interceptModeEnum.getCode(), code)) {
                return interceptModeEnum;
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
}

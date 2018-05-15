package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums;

import com.github.jiahaowen.spring.assistant.component.migration.common.util.ObjectUtil;

/**
 * 运行模式
 *
 * @author jiahaowen
 * @version $Id: RunModeEnum.java, v 0.1 2016-11-25 下午2:01 jiahaowen Exp $
 */
public enum RunModeEnum {
    OFF(0 << 0, "关闭"),
    /** 模糊判定 通过事务判定，不存在事务的场景* */
    READ(1 << 0, "读"),
    /** 模糊判定 通过事务判定，存在事务的场景* */
    WRITE(1 << 1, "写"),

    ALL(READ.getCode() | WRITE.getCode(), "读写"),
    ;

    RunModeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public static boolean isMatch(final int i, final RunModeEnum runModeEnum) {
        final boolean b = (i & runModeEnum.getCode()) > 0;
        return (runModeEnum == OFF && i == runModeEnum.code) ? !b : b;
    }

    /**
     * 根据枚举值找到
     *
     * @param value
     * @return
     */
    public static RunModeEnum getEnumByCode(Integer value) {
        for (RunModeEnum runModeEnum : RunModeEnum.values()) {
            if (ObjectUtil.equals(runModeEnum.getCode(), value)) {
                return runModeEnum;
            }
        }
        return null;
    }
}

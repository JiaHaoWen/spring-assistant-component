/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.migration.common.error;

import java.io.Serializable;

/**
 * @author chuanmu
 * @since 2017/11/20
 */
public class CommonError implements Serializable {

    /** 序列ID */
    private static final long serialVersionUID = -5579322409204568196L;

    /** 错误描述 */
    private String errorMsg;

    /** 错误发生系统 */
    private String location;

    // ~~~ 构造方法

    /** 默认构造方法 */
    public CommonError() {}

    /**
     * 构造方法
     *
     * @param msg
     * @param location
     */
    public CommonError(String msg, String location) {
        this.errorMsg = msg;
        this.location = location;
    }

    // ~~~ 重写方法

    /** @see Object#toString() */
    @Override
    public String toString() {

        return location + "::" + errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

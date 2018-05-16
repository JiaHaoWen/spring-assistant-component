package com.github.jiahaowen.spring.assistant.component.util.common.error;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

/**
 * 错误上下文对象。
 *
 * <p>错误上下文对象包含标准错误对象的堆栈，和第三方错误信息。
 *
 * @author chuanmu
 * @since 2017/11/20
 */
public class ErrorContext implements java.io.Serializable {

    /** 序列ID */
    private static final long serialVersionUID = 6436061876740168390L;

    /** 错误堆栈集合 */
    private List<CommonError> errorStack = Lists.newArrayList();

    /** 第三方错误原始信息 */
    private String thirdPartyError;

    /** 默认分隔符 */
    private static final String SPLIT = "|";

    // ~~~ 构造方法

    /** 默认构造方法 */
    public ErrorContext() {}

    // ~~~ 公共方法

    /**
     * 获取当前错误对象
     *
     * @return
     */
    public CommonError fetchCurrentError() {

        if (errorStack != null && errorStack.size() > 0) {

            return errorStack.get(errorStack.size() - 1);
        }
        return null;
    }

    /**
     * 获取当前错误码
     *
     * @return
     */
    public String fetchCurrentErrorCode() {

        if (errorStack != null && errorStack.size() > 0) {

            return errorStack.get(errorStack.size() - 1).getErrorMsg();
        }
        return null;
    }

    /**
     * 获取原始错误对象
     *
     * @return
     */
    public CommonError fetchRootError() {

        if (errorStack != null && errorStack.size() > 0) {
            return errorStack.get(0);
        }
        return null;
    }

    /**
     * 向堆栈中添加错误对象。
     *
     * @param error
     */
    public void addError(CommonError error) {

        if (errorStack == null) {

            errorStack = new ArrayList<CommonError>();
        }
        errorStack.add(error);
    }

    /**
     * 转化为简单字符串表示。
     *
     * @return
     */
    public String toDigest() {

        StringBuffer sb = new StringBuffer();

        for (int i = errorStack.size(); i > 0; i--) {

            if (i == errorStack.size()) {

                sb.append(digest(errorStack.get(i - 1)));
            } else {

                sb.append(SPLIT).append(digest(errorStack.get(i - 1)));
            }
        }
        return sb.toString();
    }

    // ~~~ 重写方法

    /** @see Object#toString() */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();

        for (int i = errorStack.size(); i > 0; i--) {

            if (i == errorStack.size()) {

                sb.append(errorStack.get(i - 1));
            } else {

                sb.append(SPLIT).append(errorStack.get(i - 1));
            }
        }
        return sb.toString();
    }

    // ~~~ 内部方法

    /**
     * 获取错误对象简单表示
     *
     * @param commonError
     * @return
     */
    private String digest(CommonError commonError) {

        if (null == commonError) {

            return null;
        }

        return commonError.toString();
    }

    // ~~~ bean方法

    public List<CommonError> getErrorStack() {
        return errorStack;
    }

    public void setErrorStack(List<CommonError> errorStack) {
        this.errorStack = errorStack;
    }

    public String getThirdPartyError() {
        return thirdPartyError;
    }

    public void setThirdPartyError(String thirdPartyError) {
        this.thirdPartyError = thirdPartyError;
    }

    public static String getSPLIT() {
        return SPLIT;
    }
}

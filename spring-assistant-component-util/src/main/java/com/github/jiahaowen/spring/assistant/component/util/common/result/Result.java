package com.github.jiahaowen.spring.assistant.component.util.common.result;

import com.github.jiahaowen.spring.assistant.component.util.common.error.ErrorContext;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 泛型Result结果类。
 *
 * @author chuanmu
 * @since 2017/11/17
 */
public class Result<T> implements Serializable {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 2289133962088317524L;

    /**
     * 结果标识
     *
     * <p><b>success为true，表示明确的成功状态</b> <b>success为false，表示明确的失败状态</b>
     */
    private boolean success;

    /** 错误上下文。 */
    private ErrorContext errorContext;

    /**
     * 返回结果实例
     *
     * <p><b>存储要返回的结果实例</b>
     */
    private T resultObj;

    /** 构造函数。 */
    public Result() {}

    /**
     * 构造函数
     * @param success
     */
    public Result(boolean success){
        this.success = success;
    }

    /**
     * 构造函数。
     *
     * @param success 是否成功。
     * @param errorContext 错误上下文。
     * @param resultObj 对象实例。
     */
    public Result(boolean success, ErrorContext errorContext, T resultObj) {

        this.success = success;
        this.errorContext = errorContext;
        this.resultObj = resultObj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResultObj() {
        return resultObj;
    }

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }

    /**
     * 必须是ToStringStyle.SHORT_PREFIX_STYLE，形式如下:</br> Person[name=John Doe,age=33,smoker=false]</br>
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

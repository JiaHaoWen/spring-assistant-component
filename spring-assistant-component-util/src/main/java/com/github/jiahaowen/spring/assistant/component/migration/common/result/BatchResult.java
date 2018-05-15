/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.migration.common.result;

import com.github.jiahaowen.spring.assistant.component.migration.common.error.ErrorContext;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 混合返回对象，覆盖处理成功，处理失败结果。
 *
 * @author chuanmu
 * @since 2017/11/17
 */
public class BatchResult<T> implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -7335854753961525278L;

    /**
     * 结果标识，只要存在失败的场景，即为false。严格校验成功的情况
     *
     * <p><b>success为true，表示明确的成功状态</b> <b>success为false，表示明确的失败状态</b>
     */
    private boolean success;

    /**
     * 返回成功结果实例
     *
     * <p><b>存储需要返回的成功结果实例</b>
     */
    private List<T> sucResultObj;

    /**
     * 返回失败结果实例
     *
     * <p><b>存储需要返回的失败结果实例</b>
     */
    private List<Result<T>> failedResultObj;

    /**
     * 错误上下文
     *
     * <p><b>当结果为false时：返回不为空</b> <b>当结果为true时：返回部分场景不为空</b>
     */
    private ErrorContext errorContext;

    /** 构建成功的空集合对象 */
    public BatchResult() {
        this(true, Lists.newArrayList(), Lists.newArrayList(), new ErrorContext());
    }

    /**
     * 构建指定接口的空集合对象
     *
     * @param success
     */
    public BatchResult(boolean success) {
        this(success, Lists.newArrayList(), Lists.newArrayList(), new ErrorContext());
    }

    /**
     * 批量结果
     *
     * @param success 成功失败
     * @param sucResultObj 成功列表
     * @param failedResultObj 失败列表
     */
    public BatchResult(
            boolean success,
            List<T> sucResultObj,
            List<Result<T>> failedResultObj,
            ErrorContext errorContext) {
        this.success = success;
        this.sucResultObj = sucResultObj;
        this.failedResultObj = failedResultObj;
        this.errorContext = errorContext;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getSucResultObj() {
        return sucResultObj;
    }

    public void setSucResultObj(List<T> sucResultObj) {
        this.sucResultObj = sucResultObj;
    }

    public List<Result<T>> getFailedResultObj() {
        return failedResultObj;
    }

    public void setFailedResultObj(List<Result<T>> failedResultObj) {
        this.failedResultObj = failedResultObj;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }

    /** @see Object#toString() */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

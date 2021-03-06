package com.github.jiahaowen.spring.assistant.component.util.common.result;


import com.github.jiahaowen.spring.assistant.component.util.common.error.ErrorContext;
import com.github.jiahaowen.spring.assistant.component.util.common.error.util.ErrorUtil;

/**
 * 返回结果工具类
 *
 * @author chuanmu
 * @since 2017/11/20
 */
public class ResultUtil {

    /**
     * 根据异常信息生成返回值
     *
     * @param <T> 泛型对象
     * @param isSuc 处理结果
     * @param message 异常信息
     * @return 生成返回结果
     */
    public static <T> Result<T> generateResult(Boolean isSuc, String message) {

        Result<T> Result = new Result<>();

        Result.setSuccess(isSuc);

        // 构建错误上下文
        ErrorContext errorContext = ErrorUtil.makeAndAddError(message);

        Result.setErrorContext(errorContext);

        return Result;
    }
}

/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.migration.common.error.util;

import com.github.jiahaowen.spring.assistant.component.migration.common.error.CommonError;
import com.github.jiahaowen.spring.assistant.component.migration.common.error.ErrorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 标准错误码工具类。
 *
 * <p>使用标准错误码时，必须配置ErrorUtil的bean。否则获取不到错误位置信息。
 *
 * @author chuanmu
 * @since 2017/11/20
 */
public class ErrorUtil {

    /** 默认日志类 */
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorUtil.class);

    /** 系统名称 */
    private static String appName;

    // ~~~ 构造方法

    private ErrorUtil() {}

    /**
     * 创建一个CommonError
     *
     * @param message
     * @return
     */
    public static CommonError makeError(String message) {

        CommonError error = new CommonError();
        error.setErrorMsg(message);
        error.setLocation(getAppName());
        return error;
    }

    /**
     * 创建一个CommonError
     *
     * @param message
     * @param location
     * @return
     */
    public static CommonError makeError(String message, String location) {

        CommonError error = new CommonError();
        error.setErrorMsg(message);
        error.setLocation(location);
        return error;
    }

    /**
     * 增加一个error到errorContext中
     *
     * @param error
     * @return
     */
    public static ErrorContext addError(CommonError error) {
        return addError(null, error);
    }

    /**
     * 增加一个error到errorContext中
     *
     * @param context
     * @param error
     * @return
     */
    public static ErrorContext addError(ErrorContext context, CommonError error) {

        if (context == null) {
            context = new ErrorContext();
        }

        if (error == null) {

            LOGGER.error("增加到ErrorContext中的CommonError不能为空");
            return context;
        }

        context.addError(error);

        return context;
    }

    /**
     * 创建并且增加一个Error到errorContext中
     *
     * @param context
     * @param message
     * @return
     */
    public static ErrorContext makeAndAddError(
            ErrorContext context, String message) {

        CommonError error = makeError(message);
        context = addError(context, error);

        return context;
    }

    /**
     * 创建并且增加一个Error到新的errorContext中
     *
     * @param message
     * @return
     */
    public static ErrorContext makeAndAddError(String message) {

        CommonError error = makeError(message);
        ErrorContext context = addError(error);

        return context;
    }

    /**
     * 获取系统名称
     *
     * @return
     */
    public static String getAppName() {

        if (null == appName || "".equals(appName)) {
            return "unknown";
        }

        return appName;
    }

    public static void setAppName(String appName) {
        ErrorUtil.appName = appName;
    }
}

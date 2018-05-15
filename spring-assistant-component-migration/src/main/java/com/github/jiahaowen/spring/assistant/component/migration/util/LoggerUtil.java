package com.github.jiahaowen.spring.assistant.component.migration.util;

import com.github.jiahaowen.spring.assistant.component.migration.common.util.StringUtil;
import com.github.jiahaowen.spring.assistant.component.migration.util.constans.LoggerConstants;
import com.github.jiahaowen.spring.assistant.component.migration.util.thread.BizThreadLocal;
import org.slf4j.Logger;

/**
 * 日志工具类
 *
 * @author jiahaowen
 * @version $Id: LoggerUtil.java, v 0.1 2014-11-12 下午8:43:03 jiahaowen Exp $
 */
public final class LoggerUtil {

    /** 防止实例化 */
    private LoggerUtil() {}

    /**
     * 生成<font color="blue">调试</font>级别日志。
     *
     * <p>可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger 日志打印对象
     * @param desc 日志描述信息
     * @param arrangementNo 合约编号[可空]
     * @param obj 任意个要输出到日志的参数[可空]
     */
    public static void debug(Logger logger, String desc, String arrangementNo, Object... obj) {

        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(desc, arrangementNo, obj));
        }
    }

    /**
     * 生成<font color="blue">通知</font>级别日志。
     *
     * <p>可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger 日志打印对象
     * @param desc 日志描述信息
     * @param arrangementNo 合约编号[可空]
     * @param obj 任意个要输出到日志的参数[可空]
     */
    public static void info(Logger logger, String desc, String arrangementNo, Object... obj) {

        if (logger.isInfoEnabled()) {
            logger.info(getLogString(desc, arrangementNo, obj));
        }
    }

    /**
     * 生成<font color="brown">警告</font>级别日志。不带异常堆栈
     *
     * <p>可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费。
     *
     * @param logger 日志打印对象
     * @param desc 日志描述信息
     * @param arrangementNo 合约编号[可空]
     * @param obj 任意个要输出到日志的参数[可空]
     */
    public static void warn(Logger logger, String desc, String arrangementNo, Object... obj) {

        logger.warn(getLogString(desc, arrangementNo, obj));
    }

    /**
     * 生成<font color="brown">警告</font>级别日志。带异常堆栈
     *
     * <p>可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费。
     *
     * @param logger 日志打印对象
     * @param desc 日志描述信息
     * @param e 异常堆栈
     * @param arrangementNo 合约编号[可空]
     * @param obj 任意个要输出到日志的参数[可空]
     */
    public static void warn(
            Logger logger, String desc, Throwable e, String arrangementNo, Object... obj) {

        logger.warn(getLogString(desc, arrangementNo, obj), e);
    }

    /**
     * 生成<font color="red">错误</font>级别日志。不带异常堆栈
     *
     * <p>可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger 日志打印对象
     * @param desc 日志描述信息
     * @param arrangementNo 合约编号[可空]
     * @param obj 任意个要输出到日志的参数[可空]
     */
    public static void error(Logger logger, String desc, String arrangementNo, Object... obj) {

        logger.error(getLogString(desc, arrangementNo, obj));
    }

    /**
     * 生成<font color="red">错误</font>级别日志。
     *
     * <p>可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger 日志打印对象
     * @param desc 日志描述信息
     * @param e 异常堆栈
     * @param arrangementNo 合约编号[可空]
     * @param obj 任意个要输出到日志的参数[可空]
     */
    public static void error(
            Logger logger, String desc, Throwable e, String arrangementNo, Object... obj) {

        logger.error(getLogString(desc, arrangementNo, obj), e);
    }

    /**
     * error日志输出
     *
     * @param logger 日志对象
     * @param message 打印信息
     * @param e 异常堆栈
     */
    public static void error(Logger logger, Object message, Throwable e) {
        logger.error(message + getLogKey(), e);
    }

    /**
     * 得到LogKey
     *
     * @return 日志key
     */
    public static String getLogKey() {

        if (BizThreadLocal.get() != null && BizThreadLocal.get().getLogKey() > 0) {
            return LoggerConstants.LOG_PARAM_PREFIX
                    + "logKey:"
                    + BizThreadLocal.get().getLogKey()
                    + LoggerConstants.LOG_PARAM_SUFFIX;
        } else {
            return StringUtil.EMPTY_STRING;
        }
    }

    /**
     * 生成输出到日志的字符串
     *
     * @param desc 日志描述信息
     * @param arrangementNo 合约编号号[可空]
     * @param obj 任意个要输出到日志的参数[可空]
     * @return 输出到日志的字符串
     */
    private static String getLogString(String desc, String arrangementNo, Object... obj) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(desc).append(getLogKey()).append(LoggerConstants.LOG_PREFIX);

        // 如果传入合约号为空，则设置为-
        if (StringUtil.isBlank(arrangementNo)) {
            stringBuilder.append(LoggerConstants.LOG_DEFAULT);
        } else {
            stringBuilder.append(arrangementNo);
        }

        if (obj != null && obj.length > 0) {

            stringBuilder.append(LoggerConstants.LOG_PARAM_PREFIX);
            for (int i = 0; i < obj.length; i++) {

                stringBuilder.append(obj[i]);
                if (obj.length - 1 != i) {

                    stringBuilder.append(LoggerConstants.LOG_SEP);
                }
            }
            stringBuilder.append(LoggerConstants.LOG_PARAM_SUFFIX);
        }
        stringBuilder.append(LoggerConstants.LOG_SUFFIX + LoggerConstants.LOG_SEP_POINT);

        return stringBuilder.toString();
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.util;

import com.github.jiahaowen.spring.assistant.component.migration.util.constans.LoggerConstants;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

/**
 * 日志拼接工具类
 *
 * @author jiahaowen.jhw
 * @version $Id: LogConstructUtil.java, v 0.1 2016-11-10 上午9:50 jiahaowen.jhw Exp $
 */
public class LogConstructUtil {

    /**
     * 日志拼接
     *
     * @param methodInvocation
     * @return
     */
    public static String constructLog(MethodInvocation methodInvocation, String result) {
        String className = methodInvocation.getClass().getName();
        String methodName = methodInvocation.getMethod().getName();
        Object[] arguments = methodInvocation.getArguments();
        String argumentStr = constructMsg(arguments);
        return constructLog(className, methodName, argumentStr, result);
    }

    /**
     * 日志拼接
     *
     * @param className
     * @param methodName
     * @param argumentStr
     * @return
     */
    public static String constructLog(
            String className, String methodName, String argumentStr, String result) {

        StringBuilder sb = new StringBuilder();

        sb.append(LoggerConstants.LOG_PREFIX);
        sb.append(result);
        sb.append(LoggerConstants.LOG_SUFFIX);

        sb.append(LoggerConstants.LOG_PREFIX);
        sb.append(LoggerConstants.LOG_PARAM_PREFIX);
        sb.append(className);
        sb.append(LoggerConstants.LOG_SEP_POINT);
        sb.append(methodName);
        sb.append(LoggerConstants.LOG_SEP);

        sb.append(LoggerConstants.LOG_SEP);
        sb.append(LoggerConstants.TIME_UNIT);
        sb.append(LoggerConstants.LOG_PARAM_SUFFIX);

        // 添加服务调用参数信息
        sb.append(argumentStr);

        sb.append(LoggerConstants.LOG_SUFFIX);

        return sb.toString();
    }

    /**
     * 构造系统日志信息
     *
     * @param object 日志对象
     * @return 构造的系统日志信息
     */
    public static String constructMsg(Object object) {

        if (object == null) {
            return LoggerConstants.LOG_DEFAULT;
        } else if (object.getClass() == String.class
                || object.getClass() == Integer.class
                || object.getClass() == Long.class) {

            // 基本类型直接输出
            return object.toString();
        } else if (object.getClass() == BigDecimal.class) {

            return String.valueOf(((BigDecimal) object).doubleValue());
        } else if (object instanceof List<?>) {

            // 如果是List的参数，将各条记录信息打印出来
            return constructListMsg(object);
        } else if (object instanceof Map<?, ?>) {
            // 如果是Map的参数，将各条记录信息打印出来
            return constructMapMsg(object);
        } else {
            return ToStringBuilder.reflectionToString(object, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    /**
     * 获取List类型的参数
     *
     * @param object
     * @return
     */
    private static String constructListMsg(Object object) {
        List<?> list = (List<?>) object;

        if (CollectionUtils.isEmpty(list)) {
            return LoggerConstants.LOG_DEFAULT;
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            final Object o = list.get(i);

            buildMsg(sb, o);
            sb.append(LoggerConstants.LOG_SEP);
        }

        return sb.toString();
    }

    /**
     * 获取Map类型的参数
     *
     * @param object
     * @return
     */
    private static String constructMapMsg(Object object) {
        Map<?, ?> map = (Map<?, ?>) object;

        if (CollectionUtils.isEmpty(map)) {
            return LoggerConstants.LOG_DEFAULT;
        }

        StringBuffer sb = new StringBuffer();

        for (Map.Entry<?, ?> e : map.entrySet()) {
            final Object key = e.getKey();
            final Object val = e.getValue();
            buildMsg(sb, key);
            sb.append(LoggerConstants.LOG_SEP_EQUAL);
            buildMsg(sb, val);
            sb.append(LoggerConstants.LOG_SEP);
        }

        return sb.toString();
    }

    /**
     * @param sb
     * @param o
     */
    private static void buildMsg(StringBuffer sb, Object o) {
        if (o.getClass() == String.class) {
            sb.append(o);
        } else {
            sb.append(ToStringBuilder.reflectionToString(o, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }
}

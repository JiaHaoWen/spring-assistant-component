package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ABTest上下文
 *
 * @author jiahaowen
 * @version $Id: ABTestThreadLocal.java, v 0.1 2017-01-06 下午11:49 jiahaowen Exp $
 */
public class ABTestThreadLocal {

    /** 本地业务线程 */
    private static final ThreadLocal<ABTestContext> LOCAL_CONTEXT =
            new ThreadLocal<ABTestContext>();

    /**
     * 从本地线程获取当前业务线程变量
     *
     * @return 业务线程变量上下文 @see BizContext
     */
    public static ABTestContext get() {
        return LOCAL_CONTEXT.get();
    }

    /**
     * 初始化业务线程变量
     *
     * @return 业务线程变量上下文 @see ABTestContext
     */
    public static ABTestContext init() {

        ABTestContext context = LOCAL_CONTEXT.get();

        if (context == null) {
            context = new ABTestContext();
            LOCAL_CONTEXT.set(context);
        }

        return context;
    }

    /**
     * 将当前业务线程变量置入本地线程
     *
     * @param context 业务线程变量上下文 @see CacheContext
     */
    public static void put(ABTestContext context) {
        LOCAL_CONTEXT.set(context);
    }

    /** 释放当前业务线程变量 */
    public static void clear() {
        LOCAL_CONTEXT.remove();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

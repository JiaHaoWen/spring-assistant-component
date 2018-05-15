package com.github.jiahaowen.spring.assistant.component.migration.util.thread;

import com.github.jiahaowen.spring.assistant.component.migration.util.context.BizContext;

/**
 * 业务线程
 *
 * @author jiahaowen
 * @version $Id: BizThreadLocal.java, v 0.1 2014-11-12 下午8:56:56 jiahaowen Exp $
 */
public class BizThreadLocal {

    /** 本地业务线程 */
    private static final ThreadLocal<BizContext> LOCAL_CONTEXT = new ThreadLocal<BizContext>();

    /**
     * 从本地线程获取当前业务线程变量
     *
     * @return 业务线程变量上下文 @see BizContext
     */
    public static BizContext get() {
        return LOCAL_CONTEXT.get();
    }

    /**
     * 初始化业务线程变量
     *
     * @return 业务线程变量上下文 @see BizContext
     */
    public static BizContext init() {

        BizContext context = LOCAL_CONTEXT.get();

        if (context == null) {
            context = new BizContext();
            LOCAL_CONTEXT.set(context);
        }

        return context;
    }

    /**
     * 将当前业务线程变量置入本地线程
     *
     * @param context 业务线程变量上下文 @see BizContext
     */
    public static void put(BizContext context) {
        LOCAL_CONTEXT.set(context);
    }

    /** 释放当前业务线程变量 */
    public static void clear() {
        LOCAL_CONTEXT.remove();
    }
}

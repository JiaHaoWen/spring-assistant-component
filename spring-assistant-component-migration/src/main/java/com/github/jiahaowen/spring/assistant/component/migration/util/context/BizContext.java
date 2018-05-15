package com.github.jiahaowen.spring.assistant.component.migration.util.context;

/**
 * 业务线程变量上下文
 *
 * @author jiahaowen
 * @version $Id: BizContext.java, v 0.1 2014-11-13 上午9:20:22 jiahaowenExp $
 */
public class BizContext {

    /** 日志的key，一个线程生成的所有日志都将有相同的key */
    private long logKey = 0;

    /** 初始化函数 */
    public BizContext() {
        logKey = System.currentTimeMillis();
    }

    /**
     * Getter method for property <tt>logKey</tt>.
     *
     * @return property value of logKey
     */
    public long getLogKey() {
        return logKey;
    }

    /**
     * Setter method for property <tt>logKey</tt>.
     *
     * @param logKey value to be assigned to property logKey
     */
    public void setLogKey(long logKey) {
        this.logKey = logKey;
    }
}

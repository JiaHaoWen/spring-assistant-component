package com.github.jiahaowen.spring.assistant.component.util.diff.differ;

/**
 * 判断给定的对象是否为某一个类型.
 *
 * @author jiahaowen.jhw
 * @version $Id: Checkable.java, v 0.1 2016-10-30 下午9:29 jiahaowen.jhw Exp $
 */
public interface Checkable {

    /**
     * 判断给定的对象是否为某一个类型.
     *
     * <p>{@code public boolean applies(Object object) { if (object instanceof Map) { return true; }
     * return false; } }
     *
     * @param object 待判断对象
     * @return
     */
    boolean applies(Object object);
}

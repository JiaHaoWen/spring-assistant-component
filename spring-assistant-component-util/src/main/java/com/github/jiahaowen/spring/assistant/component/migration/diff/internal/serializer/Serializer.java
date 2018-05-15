package com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer;

/**
 * 序列化
 *
 * @author jiahaowen.jhw
 * @version $Id: Serializer.java, v 0.1 2016-10-30 下午9:30 jiahaowen.jhw Exp $
 */
public interface Serializer<T> {

    /** 将给定的对象序列化为String */
    String serialize(T object);
}

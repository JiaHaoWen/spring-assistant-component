package com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer;

import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.serializer.NullSerializer;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.serializer.ToStringSerializer;

/**
 * @author jiahaowen.jhw
 * @version $Id: SerializerFactory.java, v 0.1 2016-10-30 下午9:34 jiahaowen.jhw Exp $
 */
public final class SerializerFactory {

    /** 构造函数 */
    public SerializerFactory() {}

    /** 对给定的对象,找到对应的序列化策略 */
    public Serializer<Object> findFor(final Object object) {

        if (object == null) {
            return NullSerializer.INSTANCE;
        }

        if (Constants.includedTypes.contains(object.getClass())) {
            return ToStringSerializer.INSTANCE;
        }

        return null;
    }
}

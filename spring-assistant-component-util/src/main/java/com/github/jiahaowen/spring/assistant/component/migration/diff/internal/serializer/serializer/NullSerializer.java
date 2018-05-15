package com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.serializer;

import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.CheckableSerializer;

/**
 * @author jiahaowen.jhw
 * @version $Id: NullSerializer.java, v 0.1 2016-10-30 下午9:37 jiahaowen.jhw Exp $
 */
public enum NullSerializer implements CheckableSerializer<Object> {
    INSTANCE;

    private static final String NULL_VALUE = "null";

    @Override
    public boolean applies(Object object) {
        return object == null;
    }

    @Override
    public String serialize(Object object) {
        return NULL_VALUE;
    }
}

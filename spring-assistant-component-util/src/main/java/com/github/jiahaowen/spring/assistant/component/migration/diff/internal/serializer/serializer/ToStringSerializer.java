package com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.serializer;

import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.CheckableSerializer;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.Constants;

/**
 * 默认序列化类
 *
 * @author jiahaowen.jhw
 * @version $Id: ToStringSerializer.java, v 0.1 2016-10-30 下午10:36 jiahaowen.jhw Exp $
 */
public enum ToStringSerializer implements CheckableSerializer<Object> {
    INSTANCE;

    @Override
    public boolean applies(Object object) {

        if (object != null) {
            if (Constants.includedTypes.contains(object.getClass())) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String serialize(Object object) {

        return object.toString();
    }
}

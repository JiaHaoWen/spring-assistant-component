package com.github.jiahaowen.spring.assistant.component.idempotent.key;

import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.Idempotent;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author jiahaowen
 */
@Component
public class DefaultReturnValue implements ReturnValueStrategy {
    @Override
    public Object value(Idempotent idempotent) {
        Class<?> clz = idempotent.returnType();
        String value = idempotent.returnValue();
        if (isCustomType(clz)) {
            return newInstance(clz);
        }
        if (value == null || "".equals(value)) {
            return getDefaultValue(clz);
        }
        return convertValue(clz, value);
    }

    private Object newInstance(Class<?> clz) {
        try {
            return clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(String.format("请为[%s]类提供一个默认的构造函数", clz.getName()));
        }
    }

    private Object convertValue(Class<?> clz, String value) {
        if (clz.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (clz.isAssignableFrom(Short.class)) {
            return Short.valueOf(value);
        } else if (clz.isAssignableFrom(Byte.class)) {
            return Byte.valueOf(value);
        } else if (clz.isAssignableFrom(Float.class)) {
            return Float.valueOf(value);
        } else if (clz.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (clz.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        } else if (clz.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(value);
        } else if (clz.isAssignableFrom(Void.class)) {
            return null;
        }
        return value;
    }

    private boolean isCustomType(Class<?> clz) {
        return !defaultTypeValue.containsKey(clz);
    }

    private Object getDefaultValue(Class<?> clz) {
        return defaultTypeValue.get(clz);
    }

    private static final Map<Class<?>, Object> defaultTypeValue = new HashMap<>(16);

    static {
        defaultTypeValue.put(String.class, null);
        defaultTypeValue.put(Integer.class, 0);
        defaultTypeValue.put(Short.class, 0);
        defaultTypeValue.put(Byte.class, 0);
        defaultTypeValue.put(Long.class, 0L);
        defaultTypeValue.put(Character.class, null);
        defaultTypeValue.put(Float.class, 0.0f);
        defaultTypeValue.put(Double.class, 0.0d);
        defaultTypeValue.put(Boolean.class, false);
        defaultTypeValue.put(Void.class, null);
    }
}

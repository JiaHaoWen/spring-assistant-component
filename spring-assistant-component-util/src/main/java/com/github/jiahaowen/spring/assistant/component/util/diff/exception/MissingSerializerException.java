package com.github.jiahaowen.spring.assistant.component.util.diff.exception;

/**
 * @author jiahaowen.jhw
 * @version $Id: MissingSerializerException.java, v 0.1 2016-10-30 下午9:28 jiahaowen.jhw Exp $
 */
public final class MissingSerializerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private MissingSerializerException(final String message) {
        super(message);
    }

    public static MissingSerializerException missingPropertySerializer(
            final String path, final Class<?> type) {
        return new MissingSerializerException(
                "路径" + path + "下,属性类型为" + type.getSimpleName() + "时,无法找到所对应的CheckableSerializer");
    }

    public static MissingSerializerException missingMapKeySerializer(
            final String path, final Class<?> type) {
        return new MissingSerializerException(
                "路径"
                        + path
                        + "下,当map中key的类型为"
                        + type.getSimpleName()
                        + "时,无法找到所对应的CheckableSerializer");
    }
}

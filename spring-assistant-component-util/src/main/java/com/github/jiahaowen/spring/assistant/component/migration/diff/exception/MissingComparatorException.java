package com.github.jiahaowen.spring.assistant.component.migration.diff.exception;

/**
 * @author jiahaowen.jhw
 * @version $Id: MissingComparatorException.java, v 0.1 2016-10-30 下午9:28 jiahaowen.jhw Exp $
 */
public final class MissingComparatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private MissingComparatorException(final String message) {
        super(message);
    }

    public static MissingComparatorException missingIterableComparator(final String path) {
        return new MissingComparatorException(
                "在路径" + path + "下,无法找到iterable中元素所对应的CheckableComparator");
    }

    public static MissingComparatorException missingMapKeyComparator(
            final String path, final Class<?> type) {
        return new MissingComparatorException(
                "路径"
                        + path
                        + "下,对key为类型: '"
                        + type.getSimpleName()
                        + "的map对象,不能找到对应的CheckableComparator");
    }
}

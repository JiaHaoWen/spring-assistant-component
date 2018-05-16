package com.github.jiahaowen.spring.assistant.component.util.diff.comparator.impl;

import com.github.jiahaowen.spring.assistant.component.util.diff.comparator.CheckableComparator;

/**
 * 自定义实现comparable接口的比较器,使用时添加
 *
 * @author jiahaowen.jhw
 * @version $Id: NaturalOrderComparator.java, v 0.1 2016-10-30 下午10:39 jiahaowen.jhw Exp $
 */
public class NaturalOrderComparator<T extends Comparable<T>> implements CheckableComparator<T> {

    private final Class<? extends Comparable<T>> type;

    private NaturalOrderComparator(final Class<? extends Comparable<T>> type) {
        this.type = type;
    }

    public static <T extends Comparable<T>> CheckableComparator<T> newInstance(
            final Class<? extends Comparable<T>> type) {
        return new NaturalOrderComparator<T>(type);
    }

    @Override
    public int compare(T first, T second) {

        if (first == null ^ second == null) {
            return (first == null) ? -1 : 1;
        }

        if (first == null && second == null) {
            return 0;
        }

        return first.compareTo(second);
    }

    @Override
    public boolean applies(Object object) {

        if (type.isInstance(object)) {
            return true;
        }

        return false;
    }
}

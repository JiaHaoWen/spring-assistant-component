package com.github.jiahaowen.spring.assistant.component.util.diff.comparator.impl;

import com.github.jiahaowen.spring.assistant.component.util.diff.comparator.CheckableComparator;

/**
 * Iterable类型排序比较器抽象类
 *
 * @author jiahaowen.jhw
 * @version $Id: IterableComparator.java, v 0.1 2016-11-02 上午12:36 jiahaowen.jhw Exp $
 */
public abstract class IterableComparator<T> implements CheckableComparator<T> {

    @Override
    public int compare(T first, T second) {

        return 0;
    }

    @Override
    public boolean applies(Object object) {

        if (object instanceof Iterable) {
            return true;
        }

        return false;
    }
}

package com.github.jiahaowen.spring.assistant.component.util.diff.comparator.impl;

import java.util.Comparator;

/**
 * @author jiahaowen.jhw
 * @version $Id: ComparableComparator.java, v 0.1 2016-10-30 下午9:31 jiahaowen.jhw Exp $
 */
public enum ComparableComparator implements Comparator<Object> {

    /** Singleton instance of this class. */
    INSTANCE;

    @Override
    public int compare(final Object first, final Object second) {
        @SuppressWarnings("unchecked")
        final Comparable<Object> comparable = (Comparable<Object>) first;
        return comparable.compareTo(second);
    }
}

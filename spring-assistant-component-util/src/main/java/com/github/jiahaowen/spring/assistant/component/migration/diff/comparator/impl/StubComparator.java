package com.github.jiahaowen.spring.assistant.component.migration.diff.comparator.impl;

import com.github.jiahaowen.spring.assistant.component.migration.diff.comparator.CheckableComparator;

/**
 * 默认对象比较器
 *
 * @author jiahaowen.jhw
 * @version $Id: StubComparator.java, v 0.1 2016-10-30 下午10:41 jiahaowen.jhw Exp $
 */
public enum StubComparator implements CheckableComparator<Object> {
    INSTANCE;

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    @Override
    public boolean applies(Object object) {

        return true;
    }
}

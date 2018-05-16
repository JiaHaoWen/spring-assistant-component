package com.github.jiahaowen.spring.assistant.component.util.diff.reflect.internal;

import com.google.common.base.Predicate;
import java.beans.PropertyDescriptor;
import javax.annotation.Nullable;

/**
 * 是否存在get方法
 *
 * @author jiahaowen.jhw
 * @version $Id: HasReadMethod.java, v 0.1 2016-10-30 下午9:47 jiahaowen.jhw Exp $
 */
public enum HasReadMethod implements Predicate<PropertyDescriptor> {
    INSTANCE;

    @Override
    public boolean apply(@Nullable PropertyDescriptor input) {
        return input != null && input.getReadMethod() != null;
    }
}

package com.github.jiahaowen.spring.assistant.component.util.diff.reflect.internal;

import com.google.common.base.Predicate;
import java.beans.PropertyDescriptor;
import javax.annotation.Nullable;

/**
 * 是否存在set方法
 *
 * @author jiahaowen.jhw
 * @version $Id: HasWriteMethod.java, v 0.1 2016-10-30 下午9:48 jiahaowen.jhw Exp $
 */
public enum HasWriteMethod implements Predicate<PropertyDescriptor> {
    INSTANCE;

    @Override
    public boolean apply(@Nullable PropertyDescriptor input) {
        return input != null && input.getWriteMethod() != null;
    }
}

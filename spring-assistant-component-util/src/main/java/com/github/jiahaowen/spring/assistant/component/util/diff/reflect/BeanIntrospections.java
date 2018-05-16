package com.github.jiahaowen.spring.assistant.component.util.diff.reflect;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.jiahaowen.spring.assistant.component.util.diff.reflect.internal.HasReadMethod;
import com.github.jiahaowen.spring.assistant.component.util.diff.reflect.internal.HasWriteMethod;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取对象运行时信息工具类 例如:得到对象在继承树上的信息\方法信息
 *
 * @author jiahaowen.jhw
 * @version $Id: BeanIntrospections.java, v 0.1 2016-10-30 下午9:45 jiahaowen.jhw Exp $
 */
public final class BeanIntrospections {

    private static final String TYPE_MUST_NOT_BE_NULL = "类型不能为空.";

    /** 判断给定的类型是否存在构造函数 */
    public static boolean hasDefaultConstructor(final Class<?> type) {

        checkNotNull(type, TYPE_MUST_NOT_BE_NULL);

        final Constructor<?>[] constructors = type.getConstructors();
        for (final Constructor<?> constructor : constructors) {

            final boolean hasZeroArguments = constructor.getParameterTypes().length == 0;
            if (hasZeroArguments) {
                return true;
            }
        }
        return false;
    }

    /** 对指定的类型,遍历得到所有的set属性集合 */
    public static Set<PropertyDescriptor> getWriteableProperties(final Class<?> type) {
        checkNotNull(type, TYPE_MUST_NOT_BE_NULL);
        return getProperties(type, HasWriteMethod.INSTANCE);
    }

    /** 对指定的类型,遍历得到所有的get属性集合 */
    public static Set<PropertyDescriptor> getReadableProperties(final Class<?> type) {
        checkNotNull(type, TYPE_MUST_NOT_BE_NULL);
        return getProperties(type, HasReadMethod.INSTANCE);
    }

    /** 对指定的类型,遍历得到所有的set/get属性集合 */
    public static Set<PropertyDescriptor> getWriteableAndReadableProperties(final Class<?> type) {
        checkNotNull(type, TYPE_MUST_NOT_BE_NULL);
        return getProperties(type, Predicates.and(HasReadMethod.INSTANCE, HasWriteMethod.INSTANCE));
    }

    /** 对指定的类型及过滤器,遍历得到所有的符合条件的属性集合 */
    private static Set<PropertyDescriptor> getProperties(
            final Class<?> type, final Predicate<? super PropertyDescriptor> predicate) {

        try {
            final BeanInfo beanInfo = ExtendBeanInfo.of(Introspector.getBeanInfo(type));
            final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            final Set<PropertyDescriptor> matchingProperties = new HashSet<PropertyDescriptor>();

            for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {

                if (predicate.apply(propertyDescriptor)) {
                    matchingProperties.add(propertyDescriptor);
                }
            }

            return matchingProperties;
        } catch (final IntrospectionException e) {
            throw new IllegalArgumentException(
                    "类型" + type.getCanonicalName() + "不能被处理.原因为: " + e.getMessage() + ".", e);
        }
    }
}

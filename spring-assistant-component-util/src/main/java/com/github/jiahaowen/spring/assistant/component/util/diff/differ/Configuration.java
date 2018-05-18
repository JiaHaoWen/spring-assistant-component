package com.github.jiahaowen.spring.assistant.component.util.diff.differ;

import static com.google.common.base.Preconditions.checkArgument;

import com.github.jiahaowen.spring.assistant.component.util.diff.comparator.CheckableComparator;
import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.concurrent.ThreadSafe;

/**
 * 排序比较策略配置类
 *
 * @author jiahaowen.jhw
 * @version $Id: Configuration.java, v 0.1 2016-10-31 下午5:11 jiahaowen.jhw Exp $
 */
@ThreadSafe
public final class Configuration {

    // TODO:待扩展属性,增加不需要比对的属性

    // TODO:待扩展属性增加,增加时间等特殊类型处理

    // TODO:待扩展属性增加,增加扩展逻辑

    // TODO:线程相关类属性比对,如volatile,transient,synchronized等特有类型属性比对

    /** 自定义不需要比对的属性 */
    private final Set<String> excludedProperties = Sets.newHashSet();

    /** 自定义不需要比对类名 */
    private final Set<String> excludedClassSimpleNames = Sets.newHashSet();

    /** 自定义不需要比对的指定路径 */
    private final Set<String> excludedPaths = Sets.newHashSet();

    /** 自定义CheckableComparator */
    private final Set<CheckableComparator> comparators = Sets.newHashSet();

    /** 自定义Comparable */
    private final Set<Class<? extends Comparable<?>>> comparables = Sets.newHashSet();

    /** Creates a new instance. */
    public Configuration() {}

    /**
     * 增加不需要比对的属性
     *
     * @param propertyName
     * @return
     */
    public Configuration excludeProperty(final String propertyName) {
        checkArgument(propertyName != null, "propertyName must not be null.");
        final Configuration copy = this.copy();
        copy.excludedProperties.add(propertyName);
        return copy;
    }

    /**
     * 增加不需要比对的属性
     *
     * @param propertyNames
     * @return
     */
    public Configuration excludePropertys(final Set<String> propertyNames) {
        checkArgument(!propertyNames.isEmpty(), "propertyName must not be null.");
        final Configuration copy = this.copy();
        copy.excludedProperties.addAll(propertyNames);
        return copy;
    }

    /**
     * 增加不需要比对的属性和类名
     *
     * @param propertyNames
     * @param excludedClassSimpleNames
     * @return
     */
    public Configuration fromPropertysAndClazzNames(
            final Set<String> propertyNames, final Set<String> excludedClassSimpleNames) {
        checkArgument(!propertyNames.isEmpty(), "propertyName must not be null.");
        final Configuration copy = this.copy();
        copy.excludedProperties.addAll(propertyNames);
        copy.excludedClassSimpleNames.addAll(excludedClassSimpleNames);
        return copy;
    }

    /**
     * 增加不需要比对的路径
     *
     * @param paths
     * @return
     */
    public Configuration excludePaths(final Set<String> paths) {
        checkArgument(!paths.isEmpty(), "paths must not be null.");
        final Configuration copy = this.copy();
        copy.excludedPaths.addAll(paths);
        return copy;
    }

    /**
     * 自定义CheckableComparator
     *
     * @param comparator
     * @return
     */
    public Configuration useComparator(final CheckableComparator<?> comparator) {
        checkArgument(comparator != null, "comparator must not be null.");
        final Configuration copy = this.copy();
        copy.comparators.add(comparator);
        return copy;
    }

    /**
     * 自定义Comparable 类型需实现Comparable接口
     *
     * @param comparable
     * @return
     */
    public Configuration useNaturalOrderingFor(final Class<? extends Comparable<?>> comparable) {
        checkArgument(comparable != null, "comparable must not be null.");
        final Configuration copy = this.copy();
        copy.comparables.add(comparable);
        return copy;
    }

    /**
     * 深拷贝
     *
     * @return
     */
    private Configuration copy() {
        final Configuration copy = new Configuration();
        copy.comparables.addAll(this.comparables);
        copy.comparators.addAll(this.comparators);
        copy.excludedProperties.addAll(this.excludedProperties);
        copy.excludedPaths.addAll(this.excludedPaths);
        return copy;
    }

    /** @return */
    Set<CheckableComparator> getCheckableComparators() {
        return comparators;
    }

    /** @return */
    Set<Class<? extends Comparable<?>>> getComparables() {
        return comparables;
    }

    /**
     * Getter method for property excludedProperties.
     *
     * @return property value of excludedProperties
     */
    public Set<String> getExcludedProperties() {
        return excludedProperties;
    }

    /**
     * Getter method for property excludedPaths.
     *
     * @return property value of excludedPaths
     */
    public Set<String> getExcludedPaths() {
        return excludedPaths;
    }

    /**
     * Getter method for property excludedClassSimpleNames.
     *
     * @return property value of excludedClassSimpleNames
     */
    public Set<String> getExcludedClassSimpleNames() {
        return excludedClassSimpleNames;
    }
}

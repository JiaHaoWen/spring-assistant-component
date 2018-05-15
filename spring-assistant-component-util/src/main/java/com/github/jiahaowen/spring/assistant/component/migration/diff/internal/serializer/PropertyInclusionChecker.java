package com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer;

import java.util.Set;
import javax.annotation.Nullable;
import org.springframework.util.CollectionUtils;

/**
 * 属性排除校验器
 *
 * @author jiahaowen.jhw
 * @version $Id: PropertyInclusionChecker.java, v 0.1 2016-12-08 上午10:29 jiahaowen.jhw Exp $
 */
public final class PropertyInclusionChecker {

    /** 排除的属性 */
    private Set<String> excludedProperties;

    /** 排除的类名 */
    private Set<String> excludedClassSimpleNames;

    /**
     * 构造函数
     *
     * @param excludedProperties
     * @param excludedClassSimpleNames
     */
    public PropertyInclusionChecker(
            final Set<String> excludedProperties, final Set<String> excludedClassSimpleNames) {
        this.excludedProperties = excludedProperties;
        this.excludedClassSimpleNames = excludedClassSimpleNames;
    }

    /**
     * 过滤指定clazzName下的属性值
     *
     * @param name
     * @param clazzName
     * @return
     */
    public boolean apply(@Nullable String name, @Nullable String clazzName) {

        if (name == null) {
            return false;
        }

        if (CollectionUtils.isEmpty(excludedProperties)) {
            return false;
        } else {
            if (CollectionUtils.isEmpty(excludedClassSimpleNames)) {
                return excludedProperties.contains(name);
            } else {
                return excludedProperties.contains(name)
                        && excludedClassSimpleNames.contains(clazzName);
            }
        }
    }
}

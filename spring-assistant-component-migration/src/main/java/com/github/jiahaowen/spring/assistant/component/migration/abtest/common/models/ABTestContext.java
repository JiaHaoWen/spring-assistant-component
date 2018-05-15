package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ABTest线程变量上下文
 *
 * @author jiahaowen
 * @version $Id: ABTestContext.java, v 0.1 2017-01-06 下午11:49 jiahaowen Exp $
 */
public class ABTestContext implements Serializable {
    private static final long serialVersionUID = -1520505803333156224L;

    private Map<Object, Object> extraMap = Maps.newHashMap();

    private AtomicInteger level = new AtomicInteger();

    public int getLevel() {
        return level.get();
    }

    public int enter() {
        return level.incrementAndGet();
    }

    public int release() {
        return level.decrementAndGet();
    }

    /**
     * Getter method for property extraMap.
     *
     * @return property value of extraMap
     */
    public Map<Object, Object> getExtraMap() {
        return extraMap;
    }

    /**
     * Setter method for property extraMap.
     *
     * @param extraMap value to be assigned to property extraMap
     */
    public void setExtraMap(Map<Object, Object> extraMap) {
        this.extraMap = extraMap;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

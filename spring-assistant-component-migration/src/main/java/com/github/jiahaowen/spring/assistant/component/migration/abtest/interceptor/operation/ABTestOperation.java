package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker.Checker;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.splitter.Splitter;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author jiahaowen
 * @version $Id: ABTestOperation.java, v 0.1 16/11/18 下午5:43 jiahaowen Exp $
 */
public class ABTestOperation implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 2711093143160275868L;

    private String methodName;
    private Splitter splitter;
    private Checker checker;

    /**
     * Getter method for property methodName.
     *
     * @return property value of methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Setter method for property methodName.
     *
     * @param methodName value to be assigned to property methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Getter method for property splitter.
     *
     * @return property value of splitter
     */
    public Splitter getSplitter() {
        return splitter;
    }

    /**
     * Setter method for property splitter.
     *
     * @param splitter value to be assigned to property splitter
     */
    public void setSplitter(Splitter splitter) {
        this.splitter = splitter;
    }

    /**
     * Getter method for property checker.
     *
     * @return property value of checker
     */
    public Checker getChecker() {
        return checker;
    }

    /**
     * Setter method for property checker.
     *
     * @param checker value to be assigned to property checker
     */
    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

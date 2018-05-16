package com.github.jiahaowen.spring.assistant.component.util.diff.differ;

import java.util.List;

/**
 * 比对结果
 *
 * @author jiahaowen.jhw
 * @version $Id: CompareResult.java, v 0.1 2016-11-02 下午11:55 jiahaowen.jhw Exp $
 */
public class CompareResult {

    /** 基准对象 */
    private List<String> baseDifference;

    /** 比对对象 */
    private List<String> compareDifference;

    /**
     * 构造器
     *
     * @param baseDifference
     * @param compareDifference
     */
    public CompareResult(List<String> baseDifference, List<String> compareDifference) {
        this.baseDifference = baseDifference;
        this.compareDifference = compareDifference;
    }

    /**
     * Getter method for property baseDifference.
     *
     * @return property value of baseDifference
     */
    public List<String> getBaseDifference() {
        return baseDifference;
    }

    /**
     * Setter method for property baseDifference.
     *
     * @param baseDifference value to be assigned to property baseDifference
     */
    public void setBaseDifference(List<String> baseDifference) {
        this.baseDifference = baseDifference;
    }

    /**
     * Getter method for property compareDifference.
     *
     * @return property value of compareDifference
     */
    public List<String> getCompareDifference() {
        return compareDifference;
    }

    /**
     * Setter method for property compareDifference.
     *
     * @param compareDifference value to be assigned to property compareDifference
     */
    public void setCompareDifference(List<String> compareDifference) {
        this.compareDifference = compareDifference;
    }
}

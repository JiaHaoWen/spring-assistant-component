package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models;

import static com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.ABTestConstants.EXTREME_POSITION;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.CheckModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.InterceptModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.RunModeEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 拦截测试模型
 *
 * @author jiahaowen
 * @version $Id: InterceptABTestModel.java, v 0.1 16/11/18 下午11:38 jiahaowen Exp $
 */
public class InterceptABTestModel {

    /** 拦截模式 */
    private InterceptModeEnum interceptModeEnum;

    /** 运行模式 */
    private RunModeEnum runModeEnum;

    /** 校验模式 */
    private CheckModeEnum checkModeEnum;

    /** 拦截方法的方法名 */
    private String methodStr;

    /** 被执行的概率(万分位) */
    private int executeFrequency = EXTREME_POSITION;

    /** 被比对概率(万分位) */
    private int checkFrequency = EXTREME_POSITION;

    /**
     * Getter method for property interceptModeEnum.
     *
     * @return property value of interceptModeEnum
     */
    public InterceptModeEnum getInterceptModeEnum() {
        return interceptModeEnum;
    }

    /**
     * Setter method for property interceptModeEnum.
     *
     * @param interceptModeEnum value to be assigned to property interceptModeEnum
     */
    public void setInterceptModeEnum(InterceptModeEnum interceptModeEnum) {
        this.interceptModeEnum = interceptModeEnum;
    }

    /**
     * Getter method for property runModeEnum.
     *
     * @return property value of runModeEnum
     */
    public RunModeEnum getRunModeEnum() {
        return runModeEnum;
    }

    /**
     * Setter method for property runModeEnum.
     *
     * @param runModeEnum value to be assigned to property runModeEnum
     */
    public void setRunModeEnum(RunModeEnum runModeEnum) {
        this.runModeEnum = runModeEnum;
    }

    /**
     * Getter method for property checkModeEnum.
     *
     * @return property value of checkModeEnum
     */
    public CheckModeEnum getCheckModeEnum() {
        return checkModeEnum;
    }

    /**
     * Setter method for property checkModeEnum.
     *
     * @param checkModeEnum value to be assigned to property checkModeEnum
     */
    public void setCheckModeEnum(CheckModeEnum checkModeEnum) {
        this.checkModeEnum = checkModeEnum;
    }

    /**
     * Getter method for property executeFrequency.
     *
     * @return property value of executeFrequency
     */
    public int getExecuteFrequency() {
        return executeFrequency;
    }

    /**
     * Setter method for property executeFrequency.
     *
     * @param executeFrequency value to be assigned to property executeFrequency
     */
    public void setExecuteFrequency(int executeFrequency) {
        this.executeFrequency = executeFrequency;
    }

    /**
     * Getter method for property methodStr.
     *
     * @return property value of methodStr
     */
    public String getMethodStr() {
        return methodStr;
    }

    /**
     * Setter method for property methodStr.
     *
     * @param methodStr value to be assigned to property methodStr
     */
    public void setMethodStr(String methodStr) {
        this.methodStr = methodStr;
    }

    /**
     * Getter method for property checkFrequency.
     *
     * @return property value of checkFrequency
     */
    public int getCheckFrequency() {
        return checkFrequency;
    }

    /**
     * Setter method for property checkFrequency.
     *
     * @param checkFrequency value to be assigned to property checkFrequency
     */
    public void setCheckFrequency(int checkFrequency) {
        this.checkFrequency = checkFrequency;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

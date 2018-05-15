package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.splitter.impl;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.CheckModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.RunModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.InterceptABTestModel;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.util.ABTestUtil;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.splitter.Splitter;
import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 基于DRM配置的分流器
 *
 * @author jiahaowen
 * @version $Id: BaseDRMSplitter.java, v 0.1 16/11/18 下午5:26 jiahaowen Exp $
 */
@Component
public class BaseDRMSplitter implements Splitter<Object> {

    @Autowired private ABTestConfig abTestConfig;

    @Override
    public boolean goBizB(Method method, Object... o) {
        final InterceptABTestModel interceptABTestModel = matchMethod(method);
        if (interceptABTestModel != null) {
            RunModeEnum runMode = interceptABTestModel.getRunModeEnum();

            if (RunModeEnum.isMatch(ABTestUtil.currentMode().getCode(), runMode)) {
                return ABTestUtil.isRandomHit(interceptABTestModel.getExecuteFrequency());
            }
        }
        return false;
    }

    @Override
    public boolean doCheckBizB(Method method, Object... t) {
        final InterceptABTestModel interceptABTestModel = matchMethod(method);
        if (interceptABTestModel != null) {
            return ABTestUtil.isRandomHit(interceptABTestModel.getCheckFrequency());
        }
        return false;
    }

    @Override
    public boolean isAsynShuntAndCheck(Method method, Object... t) {
        final InterceptABTestModel interceptABTestModel = matchMethod(method);
        // 为同步
        if (interceptABTestModel != null
                && interceptABTestModel.getCheckModeEnum() == CheckModeEnum.SYNC) {
            return false;
        }
        return true;
    }

    private InterceptABTestModel matchMethod(Method method) {
        final String methodName = method.getName();
        if (abTestConfig.getConfigMap().containsKey(methodName)) {
            return abTestConfig.getConfigMap().get(methodName);
        }
        return null;
    }

    /**
     * Getter method for property abTestConfig.
     *
     * @return property value of abTestConfig
     */
    public ABTestConfig getAbTestConfig() {
        return abTestConfig;
    }

    /**
     * Setter method for property abTestConfig.
     *
     * @param abTestConfig value to be assigned to property abTestConfig
     */
    public void setAbTestConfig(ABTestConfig abTestConfig) {
        this.abTestConfig = abTestConfig;
    }
}

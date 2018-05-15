package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.control;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.CheckModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.InterceptModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.RunModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.ABTestConstants;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ABTest 配置
 *
 * @author jiahaowen
 * @version $Id: ABTestControlConfig.java, v 0.1 2016年11月18日 下午22:54:20 jiahaowen Exp $
 */
@Component
public class ABTestControlConfig {

    /** 配置信息 */
    @Autowired private ABTestConfig abTestConfig;

    /**
     * ab测试匹配规则
     *
     * <p>true,全局关闭,关闭所有的
     *
     * <p>方法名,执行概率(万分位),比对概率(万分位)
     *
     * <p>"拦截模式":"执行模式":"校验模式":"方法名":"执行概率":"比对概率";
     *
     * <p>interceptMode:runMode:checkMode:methodName:[0-10000]:[0-10000];interceptMode:runMode:checkMode:methodName:[0-10000]:[0-10000];
     *
     * <p>不解决重载问题
     *
     * @see InterceptModeEnum
     * @see RunModeEnum
     * @see CheckModeEnum
     */
    private volatile String abTestBizValue = "";

    /** rpc服务配置规则信息 "类名":"方法名";"类名":"方法名"; */
    private volatile String rpcServiceValue = "";

    /** 系统白名单，逗号分隔 */
    private volatile String abTestSourceAppWhiteList = ABTestConstants.ALL;

    /** 过滤字段名，逗号分隔 */
    private volatile String abTestExcludeProperties = "";

    /** 严格模式 */
    private volatile String strictMode = BooleanUtils.toStringTrueFalse(true);

    /** 是否打开详细日志 */
    private volatile String openDetailLog = BooleanUtils.toStringTrueFalse(true);

    /** 过滤参数 "方法名":"参数值1#参数值2" method1:argValue1#argValue2,method2:argValue2 */
    private volatile String abTestExcludeArgs = "";

    public String getAbTestBizValue() {
        return abTestBizValue;
    }

    public String getAbTestExcludeProperties() {
        return abTestExcludeProperties;
    }

    public String getRpcServiceValue() {
        return rpcServiceValue;
    }

    public String getOpenDetailLog() {
        return openDetailLog;
    }

    public String getAbTestExcludeArgs() {
        return abTestExcludeArgs;
    }

    public String getAbTestSourceAppWhiteList() {
        return abTestSourceAppWhiteList;
    }

    public String getStrictMode() {
        return strictMode;
    }

    public void setAbTestBizValue(String abTestBizValue) {
        this.abTestBizValue = abTestBizValue;
        abTestConfig.updateABTestConfig(abTestBizValue);
    }

    public void setAbTestExcludeProperties(String abTestExcludeProperties) {
        this.abTestExcludeProperties = abTestExcludeProperties;
        abTestConfig.updateExcludeProperties(abTestExcludeProperties);
    }

    public void setStrictMode(String strictMode) {
        this.strictMode = strictMode;
        abTestConfig.updateABTestStrictMode(strictMode);
    }

    public void setRpcServiceValue(String rpcServiceValue) {
        this.rpcServiceValue = rpcServiceValue;
        abTestConfig.updateRpcServiceConfig(rpcServiceValue);
    }

    public void setOpenDetailLog(String openDetailLog) {
        this.openDetailLog = openDetailLog;
        abTestConfig.updateABTestDetailLog(openDetailLog);
    }

    public void setAbTestSourceAppWhiteList(String abTestSourceAppWhiteList) {
        this.abTestSourceAppWhiteList = abTestSourceAppWhiteList;
        abTestConfig.updateSourceAppWhiteList(abTestSourceAppWhiteList);
    }

    public void setAbTestExcludeArgs(String abTestExcludeArgs) {
        this.abTestExcludeArgs = abTestExcludeArgs;
        abTestConfig.updateABTestArgBlackMap(abTestExcludeArgs);
    }
}

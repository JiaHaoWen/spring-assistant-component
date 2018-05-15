package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ABTest动态控制服务，使用方需要实现该接口。
 *
 * <p>例如，基于阿里系DRM/Diamond等动态修改某些配置值
 *
 * @author chuanmu
 * @since 2018/5/15
 */
@Component
public abstract class ABTestControl {

    @Autowired private ABTestControlConfig abTestControlConfig;

    // TODO:待实现的动态配置犯法
    public abstract String getABTestBizValue();

    public abstract String getRpcServiceValue();

    public abstract String getABTestSourceAppWhiteList();

    public abstract String getABTestExcludeProperties();

    public abstract String getStrictMode();

    public abstract String getOpenDetailLog();

    public abstract String getABTestExcludeArgs();

    // 在上面几个抽象方法调用时，触发下面的更新逻辑
    public void abTestBizValue() {
        abTestControlConfig.setAbTestBizValue(getABTestBizValue());
    }

    public void rpcServiceValue() {
        abTestControlConfig.setRpcServiceValue(getRpcServiceValue());
    }

    public void abTestSourceAppWhiteList() {
        abTestControlConfig.setAbTestSourceAppWhiteList(getABTestSourceAppWhiteList());
    }

    public void abTestExcludeProperties() {
        abTestControlConfig.setAbTestExcludeProperties(getABTestExcludeProperties());
    }

    public void strictMode() {
        abTestControlConfig.setStrictMode(getStrictMode());
    }

    public void openDetailLog() {
        abTestControlConfig.setOpenDetailLog(getOpenDetailLog());
    }

    public void abTestExcludeArgs() {
        abTestControlConfig.setAbTestExcludeArgs(getABTestExcludeArgs());
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.control;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.control.advisor.Trigger;
import org.springframework.stereotype.Component;

/**
 * ABTest动态控制服务，使用方需要实现该接口。
 *
 * <p>set方法保证与{@link ABTestControlConfig}同名
 *
 * <p>例如，基于阿里系DRM/Diamond等动态修改某些配置值
 *
 * @author chuanmu
 * @since 2018/5/15
 */
@Component
public abstract class ABTestControl {

    // TODO:待实现的动态配置方法
    @Trigger
    public abstract String setABTestBizValue();

    @Trigger
    public abstract String setRpcServiceValue();

    @Trigger
    public abstract String setABTestSourceAppWhiteList();

    @Trigger
    public abstract String setABTestExcludeProperties();

    @Trigger
    public abstract String setStrictMode();

    @Trigger
    public abstract String setOpenDetailLog();

    @Trigger
    public abstract String setABTestExcludeArgs();
}

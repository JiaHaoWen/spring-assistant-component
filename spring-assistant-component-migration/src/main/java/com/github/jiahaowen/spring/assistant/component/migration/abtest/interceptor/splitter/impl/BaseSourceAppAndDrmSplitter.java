package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.splitter.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.ABTestConstants;
import com.github.jiahaowen.spring.assistant.component.migration.util.LoggerUtil;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 基于来源系统白名单
 *
 * @author jiahaowen
 * @version $Id: SourceAppSplitter.java, v 0.1 2017-02-10 上午12:06 jiahaowen Exp $
 */
@Component
public class BaseSourceAppAndDrmSplitter extends BaseDRMSplitter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSourceAppAndDrmSplitter.class);

    @Override
    public boolean goBizB(Method method, Object... o) {
        String callerAppName = ABTestConstants.ALL;
        // 来源应用
        try {
            callerAppName = StringUtils.upperCase(RpcContext.getContext().getRemoteHostName());
        } catch (Exception e) {
            LoggerUtil.warn(LOGGER, "获取来源应用信息异常：", e, StringUtils.EMPTY);
        }
        if (!CollectionUtils.isEmpty(getAbTestConfig().getSourceAppWhiteList())) {
            if (getAbTestConfig().getSourceAppWhiteList().contains(ABTestConstants.ALL)) {
                return super.goBizB(method, o);
            }
            if (getAbTestConfig().getSourceAppWhiteList().contains(callerAppName)) {
                return super.goBizB(method, o);
            }
        }
        return false;
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.rpc.proxy;

import com.github.jiahaowen.spring.assistant.component.migration.util.LoggerUtil;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rpc服务代理
 *
 * @author jiahaowen.jhw
 * @version $Id: RpcServiceProxy.java, v 0.1 2016-11-30 下午5:58 jiahaowen.jhw Exp $
 */
@Component
public class RpcServiceProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServiceProxy.class);

    /** 代理 */
    @Autowired private InvokeRpcServices invokeSofaTrServices;

    /**
     * rpc服务转派,调用到期望的服务器接口之上
     *
     * @return
     */
    public Object dispatch(String className, Method method, String methodName, Object[] args)
            throws Exception {

        LoggerUtil.info(
                LOGGER, "开始rpc服务代理转调,类名为:" + className + "方法名称为:" + methodName, StringUtils.EMPTY);

        Object result = invokeSofaTrServices.invoke(className, methodName, args, method);

        LoggerUtil.info(
                LOGGER, "rpc服务代理转调结束,类名为:" + className + "方法名称为:" + methodName, StringUtils.EMPTY);

        return result;
    }
}

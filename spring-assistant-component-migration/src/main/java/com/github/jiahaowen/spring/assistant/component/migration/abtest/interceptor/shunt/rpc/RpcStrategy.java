package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.rpc;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.RpcServiceInvokeModel;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.rpc.proxy.RpcServiceProxy;
import java.lang.reflect.Method;
import java.util.Map;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rpc分流组件
 *
 * @author jiahaowen.jhw
 * @version $Id: RpcStrategy.java, v 0.1 2017-02-04 下午5:52 jiahaowen.jhw Exp $
 */
@Component
public class RpcStrategy {

    /** rpc代理 */
    @Autowired private RpcServiceProxy rpcServiceProxy;

    /** 配置解析 */
    @Autowired private ABTestConfig abTestConfig;

    /**
     * rpc分流
     *
     * @param invocation
     * @return
     * @throws Exception
     */
    public Object process(Object[] args4B, MethodInvocation invocation) throws Exception {

        // 具体的服务接口
        Class serviceClass = invocation.getMethod().getDeclaringClass();
        // 接口名称
        String className = serviceClass.getSimpleName();
        // 方法
        Method method = invocation.getMethod();
        // 方法名称
        String methodName = invocation.getMethod().getName();

        Map<String, RpcServiceInvokeModel> map = abTestConfig.getRpcInvokeMap();
        String key = serviceClass.getSimpleName() + ":" + methodName;
        RpcServiceInvokeModel model = map.get(key);
        if (model != null) {
            return rpcServiceProxy.dispatch(className, method, methodName, args4B);
        } else {
            throw new Exception("需要转调的rpc服务不存在相关的配置信息:" + className + "," + methodName);
        }
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.rpc.proxy;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * rpc服务转调
 *
 * @author jiahaowen.jhw
 * @version $Id: InvokeSofaTrServices.java, v 0.1 2016-11-30 下午5:39 jiahaowen.jhw Exp $
 */
@Component
public class InvokeRpcServices {

    /** Logger */
    protected static Logger logger = Logger.getLogger(InvokeRpcServices.class);

    /**
     * @param methodName 调用的方法名
     * @param args 调用的服务的入参数组
     * @return 服务响应对象
     */
    public Object invoke(String className, String methodName, Object[] args, Method method)
            throws Exception {

        try {
            // dubbo泛化调用rpc服务
            DubboServiceFactory dubbo = DubboServiceFactory.getInstance();

            Object result =
                    dubbo.genericInvoke(className, methodName, getInvokeParamTyeps(method), args);

            return result;

        } catch (Exception e) {
            throw new Exception("rpc服务代理转派异常,类名为:" + className + ",方法名为:" + methodName, e);
        }
    }

    /**
     * 获取参数类型
     *
     * @param method
     * @return
     */
    private String[] getInvokeParamTyeps(Method method) {

        Class<?>[] parameterTypes = method.getParameterTypes();

        int len = parameterTypes.length;

        if (len == 0) {
            return new String[0];
        } else {
            String[] invokeParamTyeps = new String[len];
            for (int i = 0; i < len; i++) {
                invokeParamTyeps[i] = parameterTypes[i].getName();
            }

            return invokeParamTyeps;
        }
    }
}

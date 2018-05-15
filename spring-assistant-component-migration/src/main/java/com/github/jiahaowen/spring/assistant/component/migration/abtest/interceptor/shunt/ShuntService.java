package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.InterceptModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.InterceptABTestModel;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation.ABTestOperation;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.jvm.JvmStrategy;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.rpc.RpcStrategy;
import java.lang.reflect.Method;
import java.util.Map;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分流服务
 *
 * @author jiahaowen.jhw
 * @version $Id: ShuntService.java, v 0.1 2017-02-08 下午3:14 jiahaowen.jhw Exp $
 */
@Component
public class ShuntService {

    /** AB Test配置信息 */
    @Autowired private ABTestConfig abTestConfig;

    /** jvm内部分流 */
    @Autowired private JvmStrategy jvmStrategy;

    /** 跨系统分流 */
    @Autowired private RpcStrategy rpcStrategy;

    /**
     * 执行分流逻辑
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object shunt(Object[] args4B, MethodInvocation invocation, ABTestOperation abTestOp)
            throws Throwable {

        Method method = invocation.getMethod();

        // 获取拦截模式配置
        Map<String, InterceptABTestModel> configMap = abTestConfig.getConfigMap();
        // 当方法不存在对应的拦截配置时,走老逻辑
        if (configMap.get(method.getName()) == null) {
            return invocation.proceed();
        }

        InterceptABTestModel interceptABTestModel = configMap.get(method.getName());
        // 拦截模式
        InterceptModeEnum interceptModeEnum = interceptABTestModel.getInterceptModeEnum();

        // 1.RPC模式
        if (interceptModeEnum == InterceptModeEnum.RPC) {
            return rpcStrategy.process(args4B, invocation);
        }
        // 2.JVM模式
        if (interceptModeEnum == InterceptModeEnum.JVM) {
            return jvmStrategy.process(args4B, invocation, abTestOp);
        }
        // 不支持的模式,走老逻辑
        return invocation.proceed();
    }
}

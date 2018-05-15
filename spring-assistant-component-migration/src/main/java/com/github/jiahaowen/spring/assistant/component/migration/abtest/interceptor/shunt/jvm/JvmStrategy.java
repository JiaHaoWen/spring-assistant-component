package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.jvm;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation.ABTestOperation;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.jvm.service.FetchMethodBService;
import com.github.jiahaowen.spring.assistant.component.migration.util.LoggerUtil;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JVM内部方法分流组件
 *
 * @author jiahaowen.jhw
 * @version $Id: JvmStrategy.java, v 0.1 2017-02-04 下午5:50 jiahaowen.jhw Exp $
 */
@Component
public class JvmStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(JvmStrategy.class);

    /** 获取B服务信息 */
    @Autowired private FetchMethodBService fetchMethodBService;

    /**
     * 系统内部寻找到B方法后分流
     *
     * @param invocation
     * @return
     * @throws Exception
     */
    public Object process(Object[] args4B, MethodInvocation invocation, ABTestOperation abTestOp)
            throws Exception {

        // 获取基础信息
        Object target = invocation.getThis();
        Method method = invocation.getMethod();

        // 兼容配置错误，自行查看日志发现
        final Method methodB =
                fetchMethodBService.getBeanMethodBRef(method, abTestOp.getMethodName());
        if (methodB == null) {
            LoggerUtil.warn(
                    LOGGER, "AB Test Not Found Method" + method.getName(), StringUtils.EMPTY);
            throw new Exception(
                    "未找到B逻辑,类名为:"
                            + method.getDeclaringClass().getSimpleName()
                            + ",方法名为:"
                            + method.getName());
        }

        // 实施B逻辑
        LoggerUtil.info(LOGGER, "Execute B Method(doBiz)", StringUtils.EMPTY);
        Object retVal4B;
        try {
            retVal4B = AopUtils.invokeJoinpointUsingReflection(target, methodB, args4B);
        } catch (Throwable e) {
            LoggerUtil.error(LOGGER, "Execute B Method(doBiz) Failed", e, StringUtils.EMPTY);
            throw new Exception(
                    "B逻辑执行失败,类名为:"
                            + method.getDeclaringClass().getSimpleName()
                            + ",方法名为:"
                            + abTestOp.getMethodName());
        }

        return retVal4B;
    }
}

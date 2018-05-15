package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.ABTestThreadLocal;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.util.LogConstructUtil;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker.AsynCheckService;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker.SynCheckService;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation.ABTestOperation;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation.ABTestOperationSource;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.ShuntService;
import com.github.jiahaowen.spring.assistant.component.migration.common.util.DeepCloneUtil;
import com.github.jiahaowen.spring.assistant.component.migration.util.LoggerUtil;
import com.github.jiahaowen.spring.assistant.component.migration.util.constans.LoggerConstants;
import com.google.common.collect.Sets;
import java.lang.reflect.Method;
import java.util.Set;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * ab测试拦截器服务逻辑
 *
 * @author jiahaowen.jhw
 * @version $Id: InterceptService.java, v 0.1 2017-02-07 上午10:35 jiahaowen.jhw Exp $
 */
@Component
public class InterceptService implements InitializingBean {

    private static final Logger AB_TEST_DIGEST_LOGGER = LoggerFactory.getLogger("AB-TEST-DIGEST");

    /** 防止重复调用 */
    private static final ThreadLocal<Set<Method>> REGISTERED_METHODS =
            new ThreadLocal<Set<Method>>();

    /** 初始化标识 */
    private boolean initialized;

    /** AB Test配置信息 */
    @Autowired private ABTestConfig abTestConfig;

    /** AB Test操作信息 */
    @Autowired private ABTestOperationSource abTestOperationSource;

    /** 分流服务 */
    @Autowired private ShuntService shuntService;

    /** 异步分流,数据校验服务 */
    @Autowired private AsynCheckService asynCheckService;

    /** 同步分流,数据校验服务 */
    @Autowired private SynCheckService synCheckService;

    private boolean isInitialized() {
        return initialized;
    }

    @Override
    public final void afterPropertiesSet() throws Exception {
        initialized = true;
    }

    /**
     * 执行拦截逻辑
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object execute(MethodInvocation invocation) throws Throwable {
        // 基本信息
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        // 方法参数
        Object[] args4A = invocation.getArguments();
        // Clone参数，参数存在逻辑修改的可能
        Object[] args4B = null;

        if (!isInitialized()) {
            return invocation.proceed();
        }

        // 防止重复调用
        if (isInvokeRegistered(method)) {
            return invocation.proceed();
        }

        // 全局判断
        if (CollectionUtils.isEmpty(abTestConfig.getConfigMap())) {
            return invocation.proceed();
        }

        try {
            ABTestThreadLocal.init();

            // 防止重复调用
            registerInvoke(method);

            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
            if (targetClass == null && target != null) {
                targetClass = target.getClass();
            }

            // 获取ABTest配置(检查注解)
            final ABTestOperation abTestOp =
                    getAbTestOperationSource().getABTestOperation(method, targetClass);
            if (abTestOp == null) {
                return invocation.proceed();
            }

            // 获取分流,比对判断依据
            final boolean goBizB = abTestOp.getSplitter().goBizB(method, args4A);
            // 是否校验
            final boolean checkBizB = abTestOp.getSplitter().doCheckBizB(method, args4A);
            // 是否异步
            final boolean isAsynCheck = abTestOp.getSplitter().isAsynShuntAndCheck(method, args4A);

            // Clone参数，参数存在逻辑修改的可能
            if (goBizB) {
                args4B = DeepCloneUtil.deepClone(args4A);
            }
            // 不走分流逻辑
            if (!goBizB) {
                return invocation.proceed();
            }

            // 分流,不做数据核对
            if (goBizB && !checkBizB) {
                return shuntService.shunt(args4B, invocation, abTestOp);
            }

            // 异步分流且进行数据核对
            if (goBizB && checkBizB && isAsynCheck) {
                // 异步走分流且进行数据核对
                return asynCheckService.asynShuntAndCheck(args4B, invocation, abTestOp);
            }

            // 同步分流且进行数据核对
            if (goBizB && checkBizB && !isAsynCheck) {
                return synCheckService.synShuntAndCheck(args4B, invocation, abTestOp);
            }

            // 老逻辑最终兜底
            return invocation.proceed();

        } catch (Throwable t) {

            LoggerUtil.error(
                    AB_TEST_DIGEST_LOGGER,
                    "执行分流核对流程出现异常,走老逻辑保底:"
                            + LogConstructUtil.constructLog(invocation, LoggerConstants.NO)
                            + ";",
                    t);

            // 异常场景,老逻辑最终兜底
            return invocation.proceed();

        } finally {
            ABTestThreadLocal.clear();
            // 防止重复调用
            unRegisterInvoke(method);
        }
    }

    private void unRegisterInvoke(Method method) {
        Set<Method> set = REGISTERED_METHODS.get();
        if (set == null) {
            return;
        }
        set.remove(method);
        if (set.isEmpty()) {
            REGISTERED_METHODS.set(null);
        }
    }

    private void registerInvoke(Method method) {
        Set<Method> set = REGISTERED_METHODS.get();
        if (set == null) {
            set = Sets.newHashSet();
            REGISTERED_METHODS.set(set);
        }
        set.add(method);
    }

    private boolean isInvokeRegistered(Method method) {
        Set<Method> set = REGISTERED_METHODS.get();
        return set != null && set.contains(method);
    }

    public ABTestOperationSource getAbTestOperationSource() {
        return abTestOperationSource;
    }
}

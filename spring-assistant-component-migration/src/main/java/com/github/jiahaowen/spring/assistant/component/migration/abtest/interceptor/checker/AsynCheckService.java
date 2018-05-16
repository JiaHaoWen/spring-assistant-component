package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.ABTestThreadLocal;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.util.LogConstructUtil;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation.ABTestOperation;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.ShuntService;
import com.github.jiahaowen.spring.assistant.component.util.common.util.ArrayUtil;
import com.github.jiahaowen.spring.assistant.component.migration.util.LoggerUtil;
import com.github.jiahaowen.spring.assistant.component.migration.util.constans.ArcoreCommonConstants;
import com.github.jiahaowen.spring.assistant.component.migration.util.constans.LoggerConstants;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 异步数据校验服务
 *
 * @author jiahaowen.jhw
 * @version $Id: AsynCheckService.java, v 0.1 2017-02-08 下午3:18 jiahaowen.jhw Exp $
 */
@Component
public class AsynCheckService {

    private static final Logger AB_TEST_DIGEST_LOGGER = LoggerFactory.getLogger("AB-TEST-DIGEST");

    /** 异步线程执行器 */
    private static ExecutorService executor = Executors.newWorkStealingPool();

    /** 分流服务 */
    @Autowired private ShuntService shuntService;

    /** 配置信息 */
    @Autowired private ABTestConfig abTestConfig;

    /**
     * 异步数据校验服务
     *
     * @param methodInvocation
     * @throws Throwable
     */
    public Object asynShuntAndCheck(
            Object[] args4B, MethodInvocation methodInvocation, ABTestOperation abTestOp)
            throws Throwable {

        Object originalResult = methodInvocation.proceed();

        // 无需过滤时执行异步比对
        if (!filterByArgs(methodInvocation.getMethod().getName(), args4B)) {
            // 异步服务转调,结果比对
            executor.submit(
                    new CheckWorkerRunnable(args4B, methodInvocation, originalResult, abTestOp));
        } else {
            LoggerUtil.info(
                    AB_TEST_DIGEST_LOGGER,
                    "根据入参忽略比对:"
                            + LogConstructUtil.constructLog(
                                    methodInvocation, LoggerConstants.IGNORE),
                    StringUtils.EMPTY);
        }

        return originalResult;
    }

    /**
     * 是否根据入参过滤，目前支持String
     *
     * @param methodName 方法名
     * @param args 参数
     * @return 是否过滤
     */
    private boolean filterByArgs(String methodName, Object[] args) {

        if (ArrayUtil.isNotEmpty(args)) {

            // 只支持String类型参数过滤
            String[] strArgs = null;
            for (Object arg : args) {
                if (arg instanceof String) {
                    strArgs = (String[]) ArrayUtils.add(strArgs, arg);
                }
            }

            // 入参包含过滤参数，那么需要过滤
            List<String> excludeArgs = abTestConfig.getArgBlackMap().get(methodName);
            if (!CollectionUtils.isEmpty(excludeArgs)) {
                for (String excludeArg : excludeArgs) {
                    String[] excludeStdArg =
                            excludeArg.split(ArcoreCommonConstants.COMPART_HASHTAG);
                    if (!ArrayUtil.isEmpty(excludeStdArg)
                            && !ArrayUtil.isEmpty(strArgs)
                            && ArrayUtil.contains(strArgs, excludeStdArg)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** 异步分流比对 */
    private class CheckWorkerRunnable implements Runnable {

        private final Object[] args4B;
        /** 方法信息 */
        private final MethodInvocation methodInvocation;

        /** 原始结果 */
        private final Object originalResult;

        /** ab测试操作信息 */
        private final ABTestOperation abTestOp;

        /** 构造函数 */
        public CheckWorkerRunnable(
                Object[] args4B,
                MethodInvocation methodInvocation,
                Object originalResult,
                ABTestOperation abTestOp) {

            this.args4B = args4B;
            this.methodInvocation = methodInvocation;
            this.originalResult = originalResult;
            this.abTestOp = abTestOp;
        }

        /**
         * 转调用,结果比对执行
         *
         * @return
         * @throws Exception
         */
        @Override
        public void run() {
            try {

                ABTestThreadLocal.init();

                // 拦截方法的执行结果
                Object shuntResult = shuntService.shunt(args4B, methodInvocation, abTestOp);
                // 数据校验结果
                String checkResult = abTestOp.getChecker().doCheck(originalResult, shuntResult);

                // 日志打印
                printLog(checkResult, shuntResult);

            } catch (Throwable t) {
                LoggerUtil.error(
                        AB_TEST_DIGEST_LOGGER,
                        "异步结果比对出现异常:"
                                + LogConstructUtil.constructLog(
                                        methodInvocation, LoggerConstants.NO)
                                + ";",
                        t);
            } finally {
                ABTestThreadLocal.clear();
            }
        }

        /**
         * 日志打印
         *
         * @param checkResult
         */
        private void printLog(String checkResult, Object shuntResult) {
            if (StringUtils.isNotBlank(checkResult)) {
                final StringBuilder sb2 = new StringBuilder(100);
                // Check:比对结果详情
                sb2.append(checkResult);

                if (abTestConfig.isOpenDetailLog()) {
                    LoggerUtil.error(
                            AB_TEST_DIGEST_LOGGER,
                            sb2.toString(),
                            StringUtils.EMPTY,
                            LogConstructUtil.constructLog(methodInvocation, LoggerConstants.NO),
                            "MethodA",
                            LogConstructUtil.constructMsg(originalResult),
                            "MethodB",
                            LogConstructUtil.constructMsg(shuntResult));
                } else {
                    LoggerUtil.error(
                            AB_TEST_DIGEST_LOGGER,
                            "结果比对失败:"
                                    + LogConstructUtil.constructLog(
                                            methodInvocation, LoggerConstants.NO)
                                    + ",不一致数据:"
                                    + sb2.toString(),
                            StringUtils.EMPTY);
                }
            } else {
                LoggerUtil.info(
                        AB_TEST_DIGEST_LOGGER,
                        "结果比对成功:"
                                + LogConstructUtil.constructLog(
                                        methodInvocation, LoggerConstants.YES),
                        StringUtils.EMPTY);
            }
        }
    }
}

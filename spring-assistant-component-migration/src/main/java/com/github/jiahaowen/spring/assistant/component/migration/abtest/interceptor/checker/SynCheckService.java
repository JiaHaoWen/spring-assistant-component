package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.util.LogConstructUtil;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation.ABTestOperation;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.ShuntService;
import com.github.jiahaowen.spring.assistant.component.migration.util.LoggerUtil;
import com.github.jiahaowen.spring.assistant.component.migration.util.constans.LoggerConstants;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 同步分流及数据校验服务
 *
 * @author jiahaowen.jhw
 * @version $Id: SynCheckService.java, v 0.1 2017-02-08 下午3:41 jiahaowen.jhw Exp $
 */
@Component
public class SynCheckService {

    private static final Logger AB_TEST_DIGEST_LOGGER = LoggerFactory.getLogger("AB-TEST-DIGEST");

    /** 分流服务 */
    @Autowired private ShuntService shuntService;

    /** 配置信息 */
    @Autowired private ABTestConfig abTestConfig;

    /**
     * 同步数据分流
     *
     * @param methodInvocation
     * @param abTestOp
     * @throws Throwable
     */
    public Object synShuntAndCheck(
            Object[] args4B, MethodInvocation methodInvocation, ABTestOperation abTestOp)
            throws Throwable {

        // 原始结果
        Object originalResult = methodInvocation.proceed();

        // 分流结果
        Object shuntResult = shuntService.shunt(args4B, methodInvocation, abTestOp);

        // 数据校验
        String checkResult = abTestOp.getChecker().doCheck(originalResult, shuntResult);

        if (StringUtils.isNotBlank(checkResult)) {
            // 日志打印
            printLog(methodInvocation, checkResult, originalResult, shuntResult);

            return originalResult;
        } else {
            LoggerUtil.info(
                    AB_TEST_DIGEST_LOGGER,
                    "结果比对成功:"
                            + LogConstructUtil.constructLog(methodInvocation, LoggerConstants.YES),
                    StringUtils.EMPTY);

            return shuntResult;
        }
    }

    /**
     * 日志打印
     *
     * @param checkResult
     * @param shuntResult
     */
    private void printLog(
            MethodInvocation methodInvocation,
            String checkResult,
            Object originalResult,
            Object shuntResult) {
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
                            + LogConstructUtil.constructLog(methodInvocation, LoggerConstants.NO)
                            + ",不一致数据:"
                            + sb2.toString(),
                    StringUtils.EMPTY);
        }
    }
}

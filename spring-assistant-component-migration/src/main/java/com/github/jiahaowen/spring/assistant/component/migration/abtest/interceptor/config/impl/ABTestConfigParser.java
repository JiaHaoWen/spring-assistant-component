package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.impl;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.CheckModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.InterceptModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.RunModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.InterceptABTestModel;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.RpcServiceInvokeModel;
import com.github.jiahaowen.spring.assistant.component.migration.util.LoggerUtil;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author jiahaowen
 * @version $Id: ABTestConfigParser.java, v 0.1 16/11/19 上午12:44 jiahaowen Exp $
 */
@Component
public class ABTestConfigParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ABTestConfigParser.class);
    private static final String SEP_SEMICOLON = ";";
    private static final String SEP_COLONS = ":";
    private String text;

    /**
     * "拦截模式":"执行模式":"校验模式":"方法名":"执行概率":"比对概率";
     *
     * <p>interceptMode:runMode:checkMode:methodName:[0-10000]:[0-10000];interceptMode:runMode:checkMode:methodName:[0-10000]:[0-10000];
     *
     * @param text
     */
    public ABTestConfigParser(String text) {
        this.text = text;
    }

    /**
     * 转为rpc服务配置信息 "类名":"方法名";"类名":"方法名";
     *
     * @return
     */
    public List<RpcServiceInvokeModel> parser2RpcInvokeModel() {
        List<RpcServiceInvokeModel> rpcServiceInvokeModelList = Lists.newArrayList();
        try {
            if (StringUtils.isNotBlank(text)) {
                String[] sentences = StringUtils.split(text, SEP_SEMICOLON);
                if (!ArrayUtils.isEmpty(sentences)) {
                    for (String sentence : sentences) {
                        String[] values = StringUtils.split(sentence, SEP_COLONS);
                        if (!ArrayUtils.isEmpty(values)) {
                            RpcServiceInvokeModel stmt = new RpcServiceInvokeModel();
                            if (values.length == 4) {
                                stmt.setInterfaceName(values[0]);
                                stmt.setMethodName(values[1]);

                                rpcServiceInvokeModelList.add(stmt);
                            }
                        }
                        LoggerUtil.error(LOGGER, "parser error, text=", sentence);
                    }
                }
            }
        } catch (Throwable e) {
            LoggerUtil.error(LOGGER, "parser error, text=", text, e);
        }
        return rpcServiceInvokeModelList;
    }

    /**
     * 转为ab测试拦截配置模型
     * interceptMode:runMode:methodName:[0-10000]:[0-10000];interceptMode:runMode:methodName:[0-10000]:[0-10000];
     *
     * @return
     */
    public List<InterceptABTestModel> parser2InterceptModel() {
        List<InterceptABTestModel> interceptABTestModelList = Lists.newArrayList();
        try {
            if (StringUtils.isNotBlank(text)) {
                String[] sentences = StringUtils.split(text, SEP_SEMICOLON);
                if (!ArrayUtils.isEmpty(sentences)) {
                    for (String sentence : sentences) {
                        String[] values = StringUtils.split(sentence, SEP_COLONS);
                        if (!ArrayUtils.isEmpty(values)) {
                            InterceptABTestModel stmt = new InterceptABTestModel();
                            if (values.length == 6
                                    && NumberUtils.isNumber(StringUtils.trim(values[0]))
                                    && NumberUtils.isNumber(StringUtils.trim(values[1]))
                                    && NumberUtils.isNumber(StringUtils.trim(values[2]))
                                    && NumberUtils.isNumber(StringUtils.trim(values[4]))
                                    && NumberUtils.isNumber(StringUtils.trim(values[5]))) {
                                // 拦截模式
                                final InterceptModeEnum interceptModeEnum =
                                        InterceptModeEnum.getEnumByCode(
                                                NumberUtils.toInt(StringUtils.trim(values[0])));
                                stmt.setInterceptModeEnum(interceptModeEnum);
                                // 执行模式
                                final RunModeEnum runMode =
                                        RunModeEnum.getEnumByCode(
                                                NumberUtils.toInt(StringUtils.trim(values[1])));
                                stmt.setRunModeEnum(runMode);
                                // 校验模式
                                final CheckModeEnum checkMode =
                                        CheckModeEnum.getEnumByCode(
                                                NumberUtils.toInt(StringUtils.trim(values[2])));
                                stmt.setCheckModeEnum(checkMode);
                                // 方法名
                                stmt.setMethodStr(StringUtils.trim(values[3]));
                                // 分流概率
                                stmt.setExecuteFrequency(
                                        NumberUtils.toInt(
                                                StringUtils.trim(StringUtils.trim(values[4]))));
                                // 校验概率
                                stmt.setCheckFrequency(
                                        NumberUtils.toInt(StringUtils.trim(values[5])));

                                if (runMode != null
                                        && !ObjectUtils.equals(runMode, RunModeEnum.OFF)) {
                                    interceptABTestModelList.add(stmt);
                                }
                                continue;
                            }
                        }
                        LoggerUtil.error(LOGGER, "parser error, text=", sentence);
                    }
                }
            }
        } catch (Throwable e) {
            LoggerUtil.error(LOGGER, "parser error, text=", text, e);
        }
        return interceptABTestModelList;
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.CheckModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.InterceptModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.RunModeEnum;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.InterceptABTestModel;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.RpcServiceInvokeModel;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 该服务负责ABTest规则的动态配置
 *
 * <p>使用方可以在需要动态配置规则的地方引用该服务
 *
 * <p>例如，基于阿里系DRM/Diamond等动态修改某些配置值时，需要引用该服务实现动态配置
 *
 * <p>例如，ABTestConfig.updateABTestConfig("0:3:0:query:10000:10000;0:3:0:query2:10000:10000")
 *
 * @author jiahaowen
 * @version $Id: ABTestConfig.java, v 0.1 16/11/18 下午11:36 jiahaowen Exp $
 */
public interface ABTestConfig {

    /**
     * 全量更新rpc服务配置信息
     *
     * <p>配置规则:
     *
     * <p>rpc服务配置规则信息 "类名":"方法名";"类名":"方法名";
     *
     * @param config 配置信息
     */
    void updateRpcServiceConfig(String config);

    /**
     * 全量更新ABTesting配置,ab测试匹配规则如下
     *
     * <p>true,全局关闭,关闭所有的
     *
     * <p>方法名,执行概率(万分位),比对概率(万分位)
     *
     * <p>"拦截模式":"执行模式":"校验模式":"方法名":"执行概率":"比对概率";
     *
     * <p>interceptMode:runMode:checkMode:methodName:[0-10000]:[0-10000];interceptMode:runMode:checkMode:methodName:[0-10000]:[0-10000];
     *
     * <p>不解决重载问题
     *
     * @see InterceptModeEnum 拦截模式
     * @see RunModeEnum 执行模式
     * @see CheckModeEnum 校验模式
     * @param config 配置信息
     */
    void updateABTestConfig(String config);

    /**
     * 更新过滤字段
     *
     * <p>配置规则:
     *
     * <p>过滤字段名，逗号分隔
     *
     * @param config
     */
    void updateExcludeProperties(String config);

    /**
     * 更新来源系统白名单
     *
     * <p>配置规则:
     *
     * <p>系统白名单，逗号分隔
     *
     * @param config
     */
    void updateSourceAppWhiteList(String config);

    /**
     * 更新严格模式信息,默认为"true"
     *
     * <p>配置规则:
     *
     * <p>true
     *
     * <p>false
     *
     * @param strictMode
     */
    void updateABTestStrictMode(String strictMode);

    /**
     * 更新入参黑名单
     *
     * <p>配置规则(过滤参数)如下:
     *
     * <p>"方法名":"参数值1#参数值2"
     *
     * <p>例如:
     *
     * <p>method1:argValue1#argValue2,method2:argValue2
     *
     * @param config
     */
    void updateABTestArgBlackMap(String config);

    /**
     * 更新日志打开规则,默认为"true"
     *
     * <p>配置规则:
     *
     * <p>true
     *
     * <p>false
     *
     * @param openDetailLog
     */
    void updateABTestDetailLog(String openDetailLog);

    /**
     * 获取方法拦截配置,key为方法名
     *
     * @return
     */
    Map<String, InterceptABTestModel> getConfigMap();

    /**
     * 获取rpc标志信息
     *
     * @return
     */
    Map<String, RpcServiceInvokeModel> getRpcInvokeMap();

    /**
     * 获取应用白名单
     *
     * @return
     */
    Set<String> getSourceAppWhiteList();

    /**
     * 获取过滤字段
     *
     * @return
     */
    Set<String> getExcludeProperties();

    /**
     * 是否是严格模式，默认为true
     *
     * @return
     */
    boolean isStrictMode();

    /**
     * 是否打印详情
     *
     * @return
     */
    boolean isOpenDetailLog();

    /**
     * 获取入参黑名单
     *
     * @return
     */
    Map<String, List<String>> getArgBlackMap();
}

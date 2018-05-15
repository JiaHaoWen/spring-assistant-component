package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.InterceptABTestModel;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.RpcServiceInvokeModel;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jiahaowen
 * @version $Id: ABTestConfig.java, v 0.1 16/11/18 下午11:36 jiahaowen Exp $
 */
public interface ABTestConfig {

    /**
     * 全量更新rpc服务配置信息
     *
     * @param config
     */
    void updateRpcServiceConfig(String config);

    /**
     * 全量更新ABTesting配置
     *
     * @param config 配置信息
     */
    void updateABTestConfig(String config);

    /**
     * 更新过滤字段
     *
     * @param config
     */
    void updateExcludeProperties(String config);

    /**
     * 获取过滤字段
     *
     * @return
     */
    Set<String> getExcludeProperties();

    /**
     * 更新来源系统白名单
     *
     * @param config
     */
    void updateSourceAppWhiteList(String config);

    /**
     * 获取应用白名单
     *
     * @return
     */
    Set<String> getSourceAppWhiteList();

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
     * 是否是严格模式
     *
     * @return
     */
    boolean isStrictMode();

    /**
     * 更新严格模式信息
     *
     * @param strictMode
     */
    void updateABTestStrictMode(String strictMode);

    /**
     * 是否打印详情
     *
     * @return
     */
    boolean isOpenDetailLog();

    void updateABTestDetailLog(String openDetailLog);

    /**
     * 获取入参黑名单
     *
     * @return
     */
    Map<String, List<String>> getArgBlackMap();

    /**
     * 更新入参黑名单
     *
     * @param config
     */
    void updateABTestArgBlackMap(String config);
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.impl;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.ABTestConstants;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.InterceptABTestModel;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.RpcServiceInvokeModel;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import com.github.jiahaowen.spring.assistant.component.migration.util.constans.ArcoreCommonConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * AB Test 测试实现类
 *
 * @author jiahaowen
 * @version $Id: ABTestConfigImpl.java, v 0.1 16/11/18 下午11:37 jiahaowen Exp $
 */
@Component
public class ABTestConfigImpl implements ABTestConfig {

    private static final String SEP_COLONS = ":";

    /** B 方法配置 */
    volatile Map<String, InterceptABTestModel> configMap = Maps.newHashMap();

    /** rpc服务配置 */
    volatile Map<String, RpcServiceInvokeModel> rpcConfigMap = Maps.newHashMap();

    /** 排除的属性 */
    volatile Set<String> excludeProperties = Sets.newHashSet();

    /** 按参数排除校验 */
    volatile Map<String, List<String>> excludeArgs = Maps.newHashMap();

    /** 应用白名单 */
    volatile Set<String> sourceAppWhiteList = Sets.newHashSet();

    private boolean strictMode = true;

    private boolean openDetailLog = true;

    @Override
    public void updateRpcServiceConfig(String config) {
        Map<String, RpcServiceInvokeModel> map = Maps.newHashMap();
        if (StringUtils.isNotEmpty(config)) {
            ABTestConfigParser parser = new ABTestConfigParser(config);
            List<RpcServiceInvokeModel> invokeModels = parser.parser2RpcInvokeModel();
            for (RpcServiceInvokeModel model : invokeModels) {
                map.put(model.getInterfaceName() + ":" + model.getMethodName(), model);
            }
        }
        this.rpcConfigMap = map;
    }

    @Override
    public void updateABTestConfig(String config) {
        Map<String, InterceptABTestModel> map = Maps.newHashMap();

        if (StringUtils.isNotEmpty(config)) {
            ABTestConfigParser parser = new ABTestConfigParser(config);
            List<InterceptABTestModel> configs = parser.parser2InterceptModel();
            for (InterceptABTestModel model : configs) {
                map.put(model.getMethodStr(), model);
            }
        }
        this.configMap = map;
    }

    @Override
    public void updateExcludeProperties(String config) {
        String[] array = StringUtils.split(config, ArcoreCommonConstants.COMPART_COMMA);
        Set<String> excludeProperties = Sets.newHashSet();
        if (!ArrayUtils.isEmpty(array)) {
            for (String a : array) {
                if (StringUtils.isNotBlank(a)) {
                    excludeProperties.add(StringUtils.trim(a));
                }
            }
        }

        this.excludeProperties = excludeProperties;
    }

    @Override
    public void updateSourceAppWhiteList(String config) {
        String[] array =
                org.apache.commons.lang3.StringUtils.split(config, ABTestConstants.COMPART_COMMA);
        Set<String> sourceAppWhiteList = Sets.newHashSet();
        if (!ArrayUtils.isEmpty(array)) {
            for (String a : array) {
                if (StringUtils.isNotBlank(a)) {
                    sourceAppWhiteList.add(StringUtils.upperCase(StringUtils.trim(a)));
                }
            }
        }

        this.sourceAppWhiteList = sourceAppWhiteList;
    }

    @Override
    public void updateABTestArgBlackMap(String config) {
        String[] array =
                org.apache.commons.lang3.StringUtils.split(config, ABTestConstants.COMPART_COMMA);
        Map<String, List<String>> excludeArgs = Maps.newHashMap();
        if (!ArrayUtils.isEmpty(array)) {
            for (String a : array) {
                if (StringUtils.isNotBlank(a)) {
                    String[] argKeys = a.split(SEP_COLONS);
                    if (argKeys.length == 2) {
                        List<String> valList = excludeArgs.get(argKeys[0]);
                        if (valList == null) {
                            valList = Lists.newArrayList();
                            excludeArgs.put(argKeys[0], valList);
                        }
                        valList.add(argKeys[1]);
                    }
                }
            }
        }

        this.excludeArgs = excludeArgs;
    }

    @Override
    public Set<String> getSourceAppWhiteList() {
        return sourceAppWhiteList;
    }

    @Override
    public Set<String> getExcludeProperties() {
        return Collections.unmodifiableSet(excludeProperties);
    }

    /**
     * Getter method for property configMap.
     *
     * @return property value of configMap
     */
    public Map<String, InterceptABTestModel> getConfigMap() {
        return Collections.unmodifiableMap(configMap);
    }

    public Map<String, RpcServiceInvokeModel> getRpcInvokeMap() {
        return Collections.unmodifiableMap(rpcConfigMap);
    }

    @Override
    public void updateABTestStrictMode(String strictMode) {
        this.strictMode = BooleanUtils.toBoolean(strictMode);
    }

    /**
     * 是否打印详情
     *
     * @return
     */
    @Override
    public boolean isOpenDetailLog() {
        return openDetailLog;
    }

    @Override
    public void updateABTestDetailLog(String openDetailLog) {
        this.openDetailLog = BooleanUtils.toBoolean(openDetailLog);
    }

    @Override
    public Map<String, List<String>> getArgBlackMap() {
        return excludeArgs;
    }

    @Override
    public boolean isStrictMode() {
        return strictMode;
    }
}

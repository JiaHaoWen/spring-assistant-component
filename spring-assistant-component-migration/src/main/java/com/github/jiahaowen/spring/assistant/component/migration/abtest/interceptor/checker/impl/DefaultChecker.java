package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker.impl;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker.Checker;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import com.github.jiahaowen.spring.assistant.component.util.diff.DifferBuilder;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.Configuration;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.Differ;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DiffException;
import com.github.jiahaowen.spring.assistant.component.migration.util.LoggerUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 默认比对实现
 *
 * @author jiahaowen
 * @version $Id: DefaultChecker.java, v 0.1 16/11/18 下午5:28 jiahaowen Exp $
 */
@Component
public class DefaultChecker implements Checker<Object> {
    private final Logger logger = LoggerFactory.getLogger(DefaultChecker.class);

    /** ABTest配置* */
    @Autowired private ABTestConfig abTestConfig;

    @Override
    public String doCheck(Object t1, Object t2) {
        try {
            final Differ differ;
            if (!CollectionUtils.isEmpty(abTestConfig.getExcludeProperties())) {
                final Configuration configuration =
                        new Configuration().excludePropertys(abTestConfig.getExcludeProperties());
                differ = DifferBuilder.differ(configuration);
            } else {
                differ = DifferBuilder.differ();
            }
            final List<CompareResult> resultList = differ.compareObjectBaseMyers(t1, t2);

            final boolean isSame = CollectionUtils.isEmpty(resultList);
            if (!isSame) {
                StringBuilder sb = new StringBuilder("Diff Detail:");
                for (CompareResult i : resultList) {
                    sb.append(i.getBaseDifference());
                    sb.append(" != ");
                    sb.append(i.getCompareDifference());
                }
                return sb.toString();
            }
        } catch (DiffException e) {
            LoggerUtil.warn(logger, "Diff Failed", e, StringUtils.EMPTY);
        }
        return null;
    }
}

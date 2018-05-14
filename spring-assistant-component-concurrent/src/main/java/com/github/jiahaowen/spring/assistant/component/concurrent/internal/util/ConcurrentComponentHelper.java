/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.concurrent.internal.util;

import com.github.jiahaowen.spring.assistant.component.concurrent.internal.models.BasicOption;
import com.github.jiahaowen.spring.assistant.component.util.common.error.ServiceException;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 并发工具类，主要进行入参校验
 *
 * @author chuanmu
 * @since 2017/11/29
 */
@Component("concurrentComponentHelper")
public class ConcurrentComponentHelper<V extends BasicOption> {

    /** 最大批量提交任务数 */
    private static final Integer MAX_CONCURRENT = 50;

    /** 默认1s */
    private static final Integer DISPATCHER_TIMEOUT = 1000;

    /**
     * 入参校验
     *
     * @param input 并发请求入参
     */
    public void assertInput(final List<V> input) {

        if (CollectionUtils.isEmpty(input)) {
            throw ServiceException.buildInternalEx("并发批量处理引擎执行出错：请求参数个数为空!");
        }

        if (input.size() > getMaxBatchSize()) {
            StringBuilder sb = new StringBuilder();
            for (V cc : input) {
                sb.append(cc.getUniqueId()).append(",");
            }

            throw ServiceException.buildInternalEx(
                    "并发批量处理引擎执行出错：请求参数个数："
                            + input.size()
                            + " > "
                            + MAX_CONCURRENT
                            + ","
                            + sb.toString());

        } else {
            Set<String> uniqueIdSet = Sets.newHashSet();
            for (V e : input) {
                String uniqueId = e.getUniqueId();
                if (uniqueId == null) {
                    throw ServiceException.buildInternalEx("uniqueId不能为空,必填!" + "," + e.toString());
                }
                if (!uniqueIdSet.contains(uniqueId)) {
                    uniqueIdSet.add(uniqueId);
                } else {
                    throw ServiceException.buildInternalEx("并发批量处理引擎执行出错：存在相同请求:" + uniqueId);
                }
            }
        }
    }

    /**
     * 并发处理单个任务超时时机
     *
     * @return 返回超时时间
     */
    public int getDispatcherTimeout() {
        return DISPATCHER_TIMEOUT;
    }

    /**
     * 最大并发数量
     *
     * @return 返回最大并发数量
     */
    private int getMaxBatchSize() {
        return MAX_CONCURRENT;
    }
}

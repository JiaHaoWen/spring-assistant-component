/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.concurrent.internal.task;

import com.github.jiahaowen.spring.assistant.component.util.common.error.CommonError;
import com.github.jiahaowen.spring.assistant.component.util.common.error.ErrorContext;
import com.github.jiahaowen.spring.assistant.component.util.common.result.BatchResult;
import com.github.jiahaowen.spring.assistant.component.util.common.result.Result;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ObjectUtils;

/**
 * 任务分发器
 *
 * <p>对单物理机器或者单虚拟机器可支持的线程进行统一管控。
 *
 * <p>由于单一{@link Executors} 或者 {@link ThreadPoolExecutor}实际使用时存在一定缺陷。
 *
 * <p>这里统一封装{@link ExecutorCompletionService} 和 {@link ThreadPoolTaskExecutor}。
 *
 * <p>对线程使用、任务执行进行统一管控，可支持异步、同步、异构、同构等任务。
 *
 * @author chuanmu
 * @since 2017/11/17
 */
public class CallableTaskDispatcher<T> {

    /** 默认日志类 */
    private static final Logger LOGGER = LoggerFactory.getLogger(CallableTaskDispatcher.class);

    /** 管理器 */
    private final CompletionService<Result<T>> completionService;

    /** 结果集合 */
    private final Set<Future<Result<T>>> pending = Sets.newHashSet();

    /** 默认1s */
    public long timeout = 1000;

    /**
     * 构造方法
     *
     * @param executor 执行者
     * @param timeout 超时时间
     */
    public CallableTaskDispatcher(Executor executor, long timeout) {
        completionService = new ExecutorCompletionService<>(executor);
        if (timeout > 0) {
            this.timeout = timeout;
        }
    }

    /**
     * 提交任务
     *
     * @param task
     */
    public void submitCallable(Callable<Result<T>> task) {
        Future<Result<T>> f = completionService.submit(task);
        pending.add(f);
    }

    /**
     * 获取已完成任务的返回结果
     *
     * @return response, 返回success和error的，超时的未返回结果
     */
    public List<Result<T>> takeCompleted() {
        List<Result<T>> resp = Lists.newArrayList();
        int taskNum = pending.size();
        int getNum = 0;
        while (getNum < taskNum) {
            try {
                Future<Result<T>> task = completionService.poll(timeout, TimeUnit.MILLISECONDS);
                getNum++;
                if (task == null) {
                    continue;
                }
                pending.remove(task);
                Result<T> response = task.get();
                if (response != null) {
                    resp.add(response);
                }
            } catch (Exception e) {
                LOGGER.warn("并发批量处理引擎获取结果出错：", e);
            }
        }

        return resp;
    }

    /**
     * 获取返回结果，存在请求超时的情况，通过传入querySize进行判定
     *
     * @param querySize 批量请求个数
     * @return
     */
    public BatchResult<T> takeResponse(final Integer querySize) {
        BatchResult<T> repo = new BatchResult<>();
        List<Result<T>> respList = takeCompleted();

        // 全部请求超时，respList为空集合，否则默认设置为true
        repo.setSuccess(ObjectUtils.nullSafeEquals(respList.size(), querySize));

        ErrorContext errorContext = new ErrorContext();
        for (Result<T> t : respList) {
            if (!t.isSuccess()) {
                // 只要有一个不成功，即失败
                repo.setSuccess(t.isSuccess());
                repo.getFailedResultObj().add(t);
                for (CommonError error : t.getErrorContext().getErrorStack()) {
                    errorContext.addError(error);
                }
            } else {
                repo.getSucResultObj().add(t.getResultObj());
            }
        }
        if (!repo.isSuccess()) {
            if (!ObjectUtils.nullSafeEquals(respList.size(), querySize)) {
                ErrorContext timeoutErrorContext = new ErrorContext();
                for (CommonError t : timeoutErrorContext.getErrorStack()) {
                    errorContext.addError(t);
                }
            }
            if (errorContext.getErrorStack().size() > 0) {
                repo.setErrorContext(errorContext);
            }
        }
        return repo;
    }
}

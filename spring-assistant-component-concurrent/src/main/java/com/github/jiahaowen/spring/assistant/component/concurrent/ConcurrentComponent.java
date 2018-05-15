package com.github.jiahaowen.spring.assistant.component.concurrent;

import com.github.jiahaowen.spring.assistant.component.concurrent.internal.callback.BizServiceCallBack;
import com.github.jiahaowen.spring.assistant.component.concurrent.internal.models.BasicOption;
import com.github.jiahaowen.spring.assistant.component.concurrent.internal.task.AbstractCallableTask;
import com.github.jiahaowen.spring.assistant.component.concurrent.internal.task.CallableTaskDispatcher;
import com.github.jiahaowen.spring.assistant.component.concurrent.internal.util.ConcurrentComponentHelper;
import com.github.jiahaowen.spring.assistant.component.migration.common.result.BatchResult;
import com.github.jiahaowen.spring.assistant.component.migration.common.result.Result;
import com.github.jiahaowen.spring.assistant.component.migration.common.result.ResultUtil;
import java.util.List;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 业务并发批量处理组件,对返回值BatchResult中的布尔值采取一错全错的策略。另外，单次执行错误不会阻断全局执行。
 *
 * <p>背景：
 *
 * <p>一般机器(物理机or虚拟机)可支持的最大线程数为 CPU数目*单CPU核心数*单核心线程数(若开启超线程)，同时支持的最大线程数是有一定限制的。
 *
 * <p>单一{@link Executors} 或者 {@link ThreadPoolExecutor}在实际使用时存在一定缺陷。可通过{@link
 * ExecutorCompletionService} 和 {@link ThreadPoolTaskExecutor}来进行避免。
 *
 * <p>目的：
 *
 * <p>对线程池的使用做到统一管控，业务逻辑中不需要再手工创建、维护线程，减少使用风险及系统风险
 *
 * <p>使用示例：
 *
 * <p>对某种业务场景，请实现回调接口 {@link BizServiceCallBack}.
 *
 * <p><b>切记，用注解{@code @Qualifier("实现类BeanId")}来唯一标志这个实现类.</b>
 *
 * <p><b>DEMO</b>
 *
 * <p>*****************使用并发组件****************************
 *
 * <pre>{@code
 *  //自定义的原子化业务逻辑
 *  @Autowired
 *  @Qualifier("testBizExecService")
 *  private TestBizExecService testBizExecService;
 *
 *  //引用并发组件
 *  @Autowired
 *  private ConcurrentComponentWithResult concurrentComponentWithResult;
 *
 *  public void test() {
 *
 *         BatchResult<TestResult> batchResult =
 *                                 concurrentComponent
 *                                 .execute(Lists.newArrayList(), testBizExecService);
 * }
 * }</pre>
 *
 * *****************原子业务逻辑****************************
 *
 * <pre>{@code
 * @Component("testBizExecService")
 * public class TestBizExecService implements BizServiceCallBack<Person, TestResult> {
 *
 *     @Override
 *     public Result<TestResult> doBiz(Person e) {
 *
 *            final Result<TestResult> result = new Result<TestResult>();
 *            result.setSuccess(true);
 *            result.setResultObj(new TestResult(e.getName()));
 *            return result;
 *    }
 * }
 * }</pre>
 *
 * <p>V 为某次请求的单一业务入参。 业务入参类为{@link BasicOption}的子类。 其中属性 {@link
 * BasicOption#uniqueId}为单一原子业务入参的操作唯一性标志。
 *
 * <p>O 为某次请求的返回结果。 若单次业务执行逻辑无需返回结果，则返回 {@link Void}。
 *
 * @author chuanmu
 * @since 2017/11/17
 */
@Component
public class ConcurrentComponent<V extends BasicOption, O> {

    /** 默认日志类 */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentComponent.class);

    /** 异步线程执行器 */
    private static ExecutorService executor = Executors.newWorkStealingPool();

    /** 并发组件工具类 */
    @Autowired
    @Qualifier("concurrentComponentHelper")
    private ConcurrentComponentHelper<V> concurrentComponentHelper;

    /**
     * 业务逻辑并发执行器
     *
     * @param input 入参
     * @param bizServiceCallBack 业务逻辑回调接口
     * @return 批量执行结果，分成执行成功结果、执行错误结果两部分
     */
    public BatchResult<O> execute(
            final List<V> input, BizServiceCallBack<V, O> bizServiceCallBack) {

        // 执行入参校验
        concurrentComponentHelper.assertInput(input);

        try {
            LOGGER.info("并发处理请求,并发个数为：" + input.size());

            // 初始化任务分发器
            CallableTaskDispatcher<O> callableTaskDispatcher =
                    new CallableTaskDispatcher<>(
                            executor, concurrentComponentHelper.getDispatcherTimeout());

            int i = 1;
            for (final V cc : input) {
                final BasicOption basicModel = cc;
                final String seqStr = "(" + i++ + "/" + input.size() + ")";
                LOGGER.info(seqStr + "提交处理业务逻辑。操作唯一标志为:" + String.valueOf(cc.getUniqueId()));
                // 任务提交
                callableTaskDispatcher.submitCallable(
                        new AbstractCallableTask<Result<O>>(basicModel) {
                            @Override
                            protected Result<O> request() throws Exception {

                                try {
                                    LOGGER.info(
                                            seqStr
                                                    + "处理业务逻辑。操作唯一标志为："
                                                    + String.valueOf(cc.getUniqueId()));

                                    // 执行原子化业务逻辑
                                    return bizServiceCallBack.doBiz(cc);

                                } catch (Throwable e) {
                                    String uniqueId = "NULL";
                                    // 防御性避免Null
                                    if (StringUtils.isNotBlank(cc.getUniqueId())) {
                                        uniqueId = cc.getUniqueId();
                                    }

                                    LOGGER.warn("并发批量处理引擎执行时，某个请求执行出错：uniqueId=" + uniqueId, e);

                                    return ResultUtil.generateResult(
                                            Boolean.FALSE,
                                            "并发批量处理引擎执行时，某个请求执行出错：uniqueId="
                                                    + uniqueId
                                                    + "; 错误信息如下: "
                                                    + e.getMessage());
                                }
                            }
                        });
            }

            // 获取任务执行结果
            LOGGER.info("~~~获取业务逻辑结果~~~");
            return callableTaskDispatcher.takeResponse(input.size());
        } catch (Throwable ex) {
            LOGGER.error("并发批量处理引擎出现异常：" + ex.getMessage(), ex);
            throw ex;
        }
    }
}

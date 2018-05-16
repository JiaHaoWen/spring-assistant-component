package com.github.jiahaowen.spring.assistant.component.concurrent.internal.callback;

import com.github.jiahaowen.spring.assistant.component.concurrent.internal.models.BasicOption;
import com.github.jiahaowen.spring.assistant.component.util.common.result.Result;

/**
 * 单次业务执行逻辑回调接口
 *
 * <p>实现类用注解
 *
 * <p>@Qualifier("实现类BeanId")
 *
 * <p>进行标注
 *
 * @author chuanmu
 * @since 2017/11/22
 */
public interface BizServiceCallBack<V extends BasicOption, O> {

    /**
     * 对单一业务请求进行处理
     *
     * <p>V 为某次请求的单一业务入参。 业务入参类为{@link BasicOption}的子类。 其中属性 {@link
     * BasicOption#uniqueId}为单一原子业务入参的操作唯一性标志。
     *
     * <p>O 为某次请求的返回结果。 若单次业务执行逻辑无需返回结果，则返回 {@link Void}。
     *
     * @param input 单一业务入参
     * @return Result<O> 单次业务逻辑返回结果
     */
    Result<O> doBiz(V input);
}

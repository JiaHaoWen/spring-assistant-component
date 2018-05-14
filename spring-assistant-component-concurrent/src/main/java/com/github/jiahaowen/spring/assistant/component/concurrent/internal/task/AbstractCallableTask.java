/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.concurrent.internal.task;

import com.github.jiahaowen.spring.assistant.component.concurrent.internal.models.BasicOption;
import java.util.concurrent.Callable;

/**
 * 请求任务抽象基类
 *
 * <p>注意：
 *
 * <p>这里只实现{@link Callable}的抽象基类
 *
 * @author chuanmu
 * @since 2017/11/20
 */
public abstract class AbstractCallableTask<T> implements Callable<T> {

    /** 基础信息 */
    protected final BasicOption basicModel;

    /**
     * 创建异步任务
     *
     * @param basicModel
     */
    public AbstractCallableTask(final BasicOption basicModel) {
        this.basicModel = basicModel;
    }

    /**
     * 执行请求任务
     *
     * @return
     * @throws Exception
     */
    @Override
    public T call() throws Exception {
        return request();
    }

    /**
     * 具体执行请求任务业务逻辑
     *
     * @return
     * @throws Exception
     */
    protected abstract T request() throws Exception;
}

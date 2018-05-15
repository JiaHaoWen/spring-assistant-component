package com.github.jiahaowen.spring.assistant.component.migration.abtest;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.control.ABTestControlConfig;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.InterceptService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <b>AbTest拦截器,由 @see ABTestControlConfig 控制需要进行分流\校验的配置信息</b>
 *
 * <p><b>由@see ABTest 对需要进行分流\校验的方法进行注解</b> <b>支持jvm/rpc两种拦截模式:</b>
 *
 * <ul>
 *   <li>1.jvm模式下,寻找jvm内部存在的方法(格式: 方法名+"B" )进行执行\校验
 *   <li>2.rpc模式下,远程调用存在的B服务(由uniqueId进行区分,方法名不变)进行执行\校验
 * </ul>
 *
 * <b>支持异步/同步两种分流比对方式:</b>
 *
 * <ul>
 *   <li>1.如果方法返回的数据变更非常快(毫秒级变更),建议用同步分流比对(相当于走两次业务逻辑,影响时间性能,但两次比对的结果理论上是一直的)
 *   <li>2.如果业务数据变更不频繁(非毫秒级变更),建议使用异步分流比对,减少对性能的影响
 * </ul>
 *
 * @see ABTestControlConfig
 * @author jiahaowen.jhw
 * @version $Id: ABTestAspectSupport.java, v 0.1 2017-02-06 下午4:14 jiahaowen.jhw Exp $
 */
@Component
public class ABTestAspectSupport implements MethodInterceptor {

    /** ab测试拦截器服务逻辑 */
    @Autowired private InterceptService interceptService;

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {

        return interceptService.execute(invocation);
    }
}

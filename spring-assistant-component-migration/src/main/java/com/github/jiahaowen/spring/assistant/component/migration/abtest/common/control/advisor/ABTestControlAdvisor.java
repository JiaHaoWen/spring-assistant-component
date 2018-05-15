/** WaCai Inc. Copyright (c) 2009-2018 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.control.advisor;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.control.ABTestControlConfig;
import java.lang.reflect.Method;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 拦截器
 *
 * @author chuanmu
 * @since 2018/5/15
 */
@Component
public class ABTestControlAdvisor implements AfterReturningAdvice {

    @Autowired private ABTestControlConfig abTestControlConfig;

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
            throws Throwable {

        // 获取类
        Class clz = ABTestControlConfig.class;
        // 获取方法
        Method m = clz.getDeclaredMethod(method.getName(), String.class);
        // 调用方法
        m.invoke(abTestControlConfig, returnValue);
    }
}

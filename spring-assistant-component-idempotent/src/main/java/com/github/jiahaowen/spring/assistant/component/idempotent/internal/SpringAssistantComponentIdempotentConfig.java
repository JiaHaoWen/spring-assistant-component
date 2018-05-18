package com.github.jiahaowen.spring.assistant.component.idempotent.internal;

import com.github.jiahaowen.spring.assistant.component.cache.spring.autoconfigure.MethodAnnotationPointcutAdvisor;
import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.IdempotentParam;
import com.github.jiahaowen.spring.assistant.component.idempotent.interceptor.IdempotentInterceptor;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author jiahaowen
 * @since 2017/10/14
 */
@Configuration
@ComponentScan("com.github.jiahaowen.spring.assistant.component.idempotent")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringAssistantComponentIdempotentConfig {
    /**
     * 注入拦截器
     *
     * @param idempotentInterceptor
     * @return
     */
    @Bean(name = "idempotentInterceptorAdvisor")
    @ConditionalOnBean(IdempotentInterceptor.class)
    public AbstractPointcutAdvisor idempotentInterceptorAdvisor(
            IdempotentInterceptor idempotentInterceptor) {
        AbstractPointcutAdvisor idempotentInterceptorAdvisor =
                new MethodAnnotationPointcutAdvisor(IdempotentParam.class, idempotentInterceptor);
        idempotentInterceptorAdvisor.setOrder(0);
        return idempotentInterceptorAdvisor;
    }

    /** @return */
    @Bean
    public AbstractAdvisorAutoProxyCreator idempotentInterceptorProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxy = new DefaultAdvisorAutoProxyCreator();
        proxy.setAdvisorBeanNamePrefix("idempotent");
        proxy.setProxyTargetClass(true);
        // proxy.setInterceptorNames("idempotentInterceptorAdvisor");
        // 注意此处不需要设置，否则会执行两次
        return proxy;
    }
}

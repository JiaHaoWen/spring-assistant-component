package com.github.jiahaowen.spring.assistant.component.migration.internal;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.ABTest;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.ABTestAspectSupport;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.MethodAnnotationPointcutAdvisor;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置入口。
 *
 * @author chuanmu
 * @since 2017/11/20
 */
@Configuration
@ComponentScan("com.github.jiahaowen.spring.assistant.component.migration")
public class SpringAssistantComponentServiceMigrationConfig {

    /** 注入拦截器 */
    @Bean(name = "abTestAspectSupportAdvisor")
    @ConditionalOnBean(ABTestAspectSupport.class)
    public AbstractPointcutAdvisor abTestAspectSupportAdvisor(
            ABTestAspectSupport abTestAspectSupport) {
        AbstractPointcutAdvisor abTestAspectSupportAdvisor =
                new MethodAnnotationPointcutAdvisor(ABTest.class, abTestAspectSupport);
        abTestAspectSupportAdvisor.setOrder(0);
        return abTestAspectSupportAdvisor;
    }

    @Bean
    public AbstractAdvisorAutoProxyCreator abTestAspectSupportProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxy = new DefaultAdvisorAutoProxyCreator();
        proxy.setAdvisorBeanNamePrefix("abTestAspectSupport");
        proxy.setProxyTargetClass(true);
        // proxy.setInterceptorNames("abTestAspectSupportAdvisor");
        // 注意此处不需要设置，否则会执行两次
        return proxy;
    }
}

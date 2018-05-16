package com.github.jiahaowen.spring.assistant.component.cache.spring.autoconfigure;

import com.github.jiahaowen.spring.assistant.component.cache.CacheHandler;
import com.github.jiahaowen.spring.assistant.component.cache.ICacheManager;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.Cache;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.CacheDelete;
import com.github.jiahaowen.spring.assistant.component.cache.annotation.CacheDeleteTransactional;
import com.github.jiahaowen.spring.assistant.component.cache.clone.ICloner;
import com.github.jiahaowen.spring.assistant.component.cache.lock.ILock;
import com.github.jiahaowen.spring.assistant.component.cache.script.AbstractScriptParser;
import com.github.jiahaowen.spring.assistant.component.cache.serializer.ISerializer;
import com.github.jiahaowen.spring.assistant.component.cache.spring.interceptor.CacheDeleteInterceptor;
import com.github.jiahaowen.spring.assistant.component.cache.spring.interceptor.CacheDeleteTransactionalInterceptor;
import com.github.jiahaowen.spring.assistant.component.cache.spring.interceptor.CacheMethodInterceptor;
import javax.annotation.PostConstruct;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * 对autoload-cache进行自动配置<br>
 * 需要先完成 {@link AutoloadCacheManageConfiguration AutoloadCacheManageConfiguration}的配置<br>
 * 然后执行此类中的AOP相关的配置<br>
 *
 * @author jiahaowen
 */
@Configuration
@ConditionalOnClass(name = "com.github.jiahaowen.spring.assistant.component.cache.CacheHandler")
@AutoConfigureAfter({AutoloadCacheManageConfiguration.class, DistributedLockConfiguration.class})
@ConditionalOnProperty(value = AutoloadCacheProperties.PREFIX + ".enable", matchIfMissing = true)
public class AutoloadCacheAutoConfigure {

    private static final String VALIDATOR_BEAN_NAME = "autoloadCacheAutoConfigurationValidator";

    @Autowired private AutoloadCacheProperties config;

    private final ILock lock;

    public AutoloadCacheAutoConfigure(ObjectProvider<ILock> lockProvider) {
        if (null != lockProvider) {
            lock = lockProvider.getIfAvailable();
        } else {
            lock = null;
        }
    }

    @Bean(name = VALIDATOR_BEAN_NAME)
    public CacheManagerValidator autoloadCacheAutoConfigurationValidator() {
        return new CacheManagerValidator();
    }

    @Bean(destroyMethod = "destroy")
    @ConditionalOnMissingBean(CacheHandler.class)
    @ConditionalOnBean({ICacheManager.class, AbstractScriptParser.class, ICloner.class})
    public CacheHandler autoloadCacheHandler(
            ICacheManager cacheManager, AbstractScriptParser scriptParser, ICloner cloner) {
        CacheHandler cacheHandler =
                new CacheHandler(cacheManager, scriptParser, config.getConfig(), cloner);
        cacheHandler.setLock(lock);
        return cacheHandler;
    }

    // 1. 创建通知 suixingpay.autoload.cache. 和
    // suixingpay.autoload.cache.enable-delete
    @Bean
    @ConditionalOnBean(CacheHandler.class)
    @ConditionalOnProperty(
        value = AutoloadCacheProperties.PREFIX + ".enable-read-and-write",
        matchIfMissing = true
    )
    public CacheMethodInterceptor autoloadCacheMethodInterceptor(CacheHandler cacheHandler) {
        return new CacheMethodInterceptor(cacheHandler, config);
    }

    @Bean
    @ConditionalOnBean(CacheHandler.class)
    @ConditionalOnProperty(
        value = AutoloadCacheProperties.PREFIX + ".enable-delete",
        matchIfMissing = true
    )
    public CacheDeleteInterceptor autoloadCacheDeleteInterceptor(CacheHandler cacheHandler) {
        return new CacheDeleteInterceptor(cacheHandler, config);
    }

    @Bean
    @ConditionalOnBean(CacheHandler.class)
    @ConditionalOnProperty(
        value = AutoloadCacheProperties.PREFIX + ".enable-delete",
        matchIfMissing = true
    )
    public CacheDeleteTransactionalInterceptor autoloadCacheDeleteTransactionalInterceptor(
            CacheHandler cacheHandler) {
        return new CacheDeleteTransactionalInterceptor(cacheHandler, config);
    }

    // 2.配置Advisor
    @Bean("autoloadCacheAdvisor")
    @ConditionalOnBean(CacheMethodInterceptor.class)
    public AbstractPointcutAdvisor autoloadCacheAdvisor(
            CacheMethodInterceptor cacheMethodInterceptor) {
        AbstractPointcutAdvisor cacheAdvisor =
                new MethodAnnotationPointcutAdvisor(Cache.class, cacheMethodInterceptor);
        cacheAdvisor.setOrder(config.getCacheOrder());
        return cacheAdvisor;
    }

    @Bean("autoloadCacheDeleteAdvisor")
    @ConditionalOnBean(CacheDeleteInterceptor.class)
    public AbstractPointcutAdvisor autoloadCacheDeleteAdvisor(
            CacheDeleteInterceptor cacheDeleteInterceptor) {
        AbstractPointcutAdvisor cacheDeleteAdvisor =
                new MethodAnnotationPointcutAdvisor(CacheDelete.class, cacheDeleteInterceptor);
        cacheDeleteAdvisor.setOrder(config.getDeleteCacheOrder());
        return cacheDeleteAdvisor;
    }

    @Bean("autoloadCacheDeleteTransactionalAdvisor")
    @ConditionalOnBean(CacheDeleteTransactionalInterceptor.class)
    public AbstractPointcutAdvisor autoloadCacheDeleteTransactionalAdvisor(
            CacheDeleteTransactionalInterceptor cacheDeleteTransactionalInterceptor) {
        AbstractPointcutAdvisor cacheDeleteTransactionalAdvisor =
                new MethodAnnotationPointcutAdvisor(
                        CacheDeleteTransactional.class, cacheDeleteTransactionalInterceptor);
        cacheDeleteTransactionalAdvisor.setOrder(config.getDeleteCacheTransactionalOrder());
        return cacheDeleteTransactionalAdvisor;
    }

    // 3.配置ProxyCreator
    @Bean
    @ConditionalOnBean(CacheHandler.class)
    public AbstractAdvisorAutoProxyCreator autoloadCacheAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxy = new DefaultAdvisorAutoProxyCreator();
        proxy.setAdvisorBeanNamePrefix("autoloadCache");
        proxy.setProxyTargetClass(config.isProxyTargetClass());
        // proxy.setInterceptorNames("cacheAdvisor","cacheDeleteAdvisor","cacheDeleteTransactionalAdvisor");//
        // 注意此处不需要设置，否则会执行两次
        return proxy;
    }

    static class CacheManagerValidator {

        @Autowired(required = false)
        private AbstractScriptParser scriptParser;

        @Autowired(required = false)
        private ISerializer<Object> serializer;

        @Autowired(required = false)
        private ICacheManager cacheManager;

        @PostConstruct
        public void checkHasCacheManager() {
            Assert.notNull(this.scriptParser, "No script parser could be auto-configured");
            Assert.notNull(this.serializer, "No serializer could be auto-configured");
            Assert.notNull(this.cacheManager, "No cache manager could be auto-configured");
        }
    }
}

package com.github.jiahaowen.spring.assistant.component.cache.spring.config;

import com.github.jiahaowen.spring.assistant.component.cache.ICacheManager;
import com.github.jiahaowen.spring.assistant.component.cache.clone.ICloner;
import com.github.jiahaowen.spring.assistant.component.cache.map.MapCacheManager;
import com.github.jiahaowen.spring.assistant.component.cache.spring.autoconfigure.AutoloadCacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 为了方便测试，使用配置缓存
 *
 * @author: jiahaowen
 */
@Configuration
public class AutoloadCacheConfiguration {

    @Bean
    public ICacheManager mapCacheManager(AutoloadCacheProperties config, ICloner cloner) {
        return new MapCacheManager(config.getConfig(), cloner);
    }
}

package com.github.jiahaowen.spring.assistant.component.cache.spring.autoconfigure;

import com.github.jiahaowen.spring.assistant.component.cache.dto.AutoLoadConfig;
import com.github.jiahaowen.spring.assistant.component.cache.redis.JedisClusterCacheManager;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

/** @author jiahaowen */
@ConfigurationProperties(prefix = AutoloadCacheProperties.PREFIX)
public class AutoloadCacheProperties {

    public static final String PREFIX = "autoload.cache";

    private AutoLoadConfig config = new AutoLoadConfig();

    private JedisCacheManagerConfig jedis = new JedisCacheManagerConfig();

    @Autowired private Environment env;

    private boolean namespaceEnable = true;

    private boolean proxyTargetClass = true;

    private boolean enable = true;

    /** @Cache 注解是否生效, 默认值为true */
    private boolean enableReadAndWrite = true;

    /** @DeleteCache 和 @DeleteCacheTransactional 注解是否生效, 默认值为true */
    private boolean enableDelete = true;

    /** @Cache 注解AOP执行顺序 */
    private Integer cacheOrder = Integer.MAX_VALUE;

    /** @DeleteCache 注解AOP执行顺序 */
    private Integer deleteCacheOrder = Integer.MAX_VALUE;

    /** @DeleteCacheTransactionalAspect 注解AOP执行顺序 */
    private Integer deleteCacheTransactionalOrder = 0;

    @PostConstruct
    public void init() {
        if (namespaceEnable && null != env) {
            String namespace = config.getNamespace();

            if (null == namespace || namespace.trim().length() == 0) {
                String applicationName = env.getProperty("spring.application.name");
                if (null != applicationName && applicationName.trim().length() > 0) {
                    config.setNamespace(applicationName);
                }
            }
        }
    }

    /**
     * 对{@link JedisClusterCacheManager} 进行配置
     *
     * @author jiahaowen
     */
    static class JedisCacheManagerConfig {

        /**
         * Hash的缓存时长：等于0时永久缓存；大于0时，主要是为了防止一些已经不用的缓存占用内存;hashExpire小于0时，则使用@Cache中设置的expire值（默认值为-1）。
         */
        private int hashExpire = -1;

        public int getHashExpire() {
            return hashExpire;
        }

        public void setHashExpire(int hashExpire) {
            this.hashExpire = hashExpire;
        }
    }

    public AutoLoadConfig getConfig() {
        return config;
    }

    public void setConfig(AutoLoadConfig config) {
        this.config = config;
    }

    public JedisCacheManagerConfig getJedis() {
        return jedis;
    }

    public void setJedis(JedisCacheManagerConfig jedis) {
        this.jedis = jedis;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public boolean isNamespaceEnable() {
        return namespaceEnable;
    }

    public void setNamespaceEnable(boolean namespaceEnable) {
        this.namespaceEnable = namespaceEnable;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnableReadAndWrite() {
        return enableReadAndWrite;
    }

    public void setEnableReadAndWrite(boolean enableReadAndWrite) {
        this.enableReadAndWrite = enableReadAndWrite;
    }

    public boolean isEnableDelete() {
        return enableDelete;
    }

    public void setEnableDelete(boolean enableDelete) {
        this.enableDelete = enableDelete;
    }

    public Integer getCacheOrder() {
        return cacheOrder;
    }

    public void setCacheOrder(Integer cacheOrder) {
        this.cacheOrder = cacheOrder;
    }

    public Integer getDeleteCacheOrder() {
        return deleteCacheOrder;
    }

    public void setDeleteCacheOrder(Integer deleteCacheOrder) {
        this.deleteCacheOrder = deleteCacheOrder;
    }

    public Integer getDeleteCacheTransactionalOrder() {
        return deleteCacheTransactionalOrder;
    }

    public void setDeleteCacheTransactionalOrder(Integer deleteCacheTransactionalOrder) {
        this.deleteCacheTransactionalOrder = deleteCacheTransactionalOrder;
    }
}

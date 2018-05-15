package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.rpc.cache;

/**
 * 分流代理对象缓存
 *
 * @author jiahaowen.jhw
 * @version $Id: ShuntProxyCache.java, v 0.1 2016-12-28 上午11:37 jiahaowen.jhw Exp $
 */
public interface ShuntProxyCache {

    /**
     * 取
     *
     * @param key
     * @return
     */
    public Object get(String key);

    /**
     * 入
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object);

    /**
     * 删
     *
     * @param key
     */
    public void delete(String key);
}

package com.github.jiahaowen.spring.assistant.component.idempotent.data;

/**
 * @author jiahaowen
 * @date 2017/9/13 11:06
 */
public interface IdempotentOperate {
    /**
     * 执行业务操作
     *
     * @param key 唯一key
     * @return true：可以继续操作，false：不可以继续操作
     */
    boolean proceed(String key, int expiredTime);

    /**
     * 业务回滚
     *
     * @param key 唯一key
     */
    void callback(String key);
}

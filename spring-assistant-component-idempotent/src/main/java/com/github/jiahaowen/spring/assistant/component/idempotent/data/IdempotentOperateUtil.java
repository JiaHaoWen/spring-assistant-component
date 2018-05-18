package com.github.jiahaowen.spring.assistant.component.idempotent.data;

/**
 * @author jiahaowen
 */
public class IdempotentOperateUtil {
    private static final IdempotentOperate operate = new DefaultIdempotentOperate();

    /**
     * 执行业务操作
     *
     * @param key 唯一key
     * @param expiredTime 超时时间，在当前时间间隔内，保证业务幂等性
     * @return true：可以继续操作，false：不可以继续操作
     */
    public static boolean proceed(String key, int expiredTime) {
        return operate.proceed(key, expiredTime);
    }

    /**
     * 业务操作失败后，进行的回滚操作
     *
     * @param key 唯一key
     */
    public static void callBack(String key) {
        operate.callback(key);
    }
}

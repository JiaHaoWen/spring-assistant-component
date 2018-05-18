package com.github.jiahaowen.spring.assistant.component.idempotent.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import redis.clients.jedis.Jedis;

/**
 * @author jiahaowen
 */
public class DefaultIdempotentOperate implements IdempotentOperate, InitializingBean {
    private final Logger log = LoggerFactory.getLogger(getClass());

    // TODO： 需要注入redisConnectionFactory配置
    @Autowired private JedisConnectionFactory redisConnectionFactory;

    private Jedis jedis;

    @Override
    public boolean proceed(String key, int expiredTime) {

        String string = jedis.setex(key, expiredTime, key);

        boolean result = string.equals("OK") ? true : false;

        if (!result) {
            log.warn("当前操作已经被当做重复执行的操作，key为：{}", key);
        }
        return result;
    }

    @Override
    public void callback(String key) {
        jedis.del(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedisConnection redisConnection =
                RedisConnectionUtils.getConnection(redisConnectionFactory);
        jedis = (Jedis) redisConnection.getNativeConnection();
    }
}

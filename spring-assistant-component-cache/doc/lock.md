## 分布式锁配置

为了支持在高并发，大集群环境使用，增加了分布式锁的。

现在只支持排它锁，还不支持重入锁。

需要开启分布式锁支持，需要做以下配置：

1. 在[@Cache](../Cache.java "@Cache")注解中设置 lockExpire, 并且opType必须是CacheOpType.READ_WRITE。
2. 需要往 [CacheHandler](../CacheHandler.java "CacheHandler") 中的lock 属性注入ILock实例。 框架中已经内部已经基于Redis实现了一些分布式锁：

  * ILock 
      * |-----[AbstractRedisLock](../AbstractRedisLock.java "AbstractRedisLock")
          * |-----[JedisClusterLock](../JedisClusterLock.java "JedisClusterLock")
          * |-----[ShardedJedisLock](../ShardedJedisLock.java "ShardedJedisLock")
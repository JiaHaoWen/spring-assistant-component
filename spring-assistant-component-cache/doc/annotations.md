## Annotation

### [@Cache](../Cache.java "@Cache")

    

### [@ExCache](../ExCache.java "@ExCache")

  使用场景举例：如果系统中用getUserById和getUserByName,两种方法来获取用户信息，我们可以在getUserById 时把 getUserByName 的缓存也生成。反过来getUserByName 时，也可以把getUserById 的缓存生成：

    @Cache(expire=600, key="'USER.getUserById'+#args[0]", exCache={@ExCache(expire=600, key="'USER.getUserByName'+#retVal.name")})
    public User getUserById(Long id){... ...}
    
    @Cache(expire=600, key="'USER.getUserByName'+#args[0]", exCache={@ExCache(expire=600, key="'USER.getUserById'+#retVal.id")})
    public User getUserByName(Long id){... ...}
    
    
  

### [@CacheDelete](../CacheDelete.java "@CacheDelete") 删除缓存注解

### [@CacheDeleteKey](../CacheDeleteKey.java "@CacheDeleteKey") 生成删除缓存Key注解

### [@CacheDeleteTransactional](../CacheDeleteTransactional.java "@CacheDeleteTransactional") 事务环境中批量删除缓存注解
    
### [@LocalCache](../LocalCache.java "@LocalCache") 本地缓存注解

### 简介
* 缓存技术五花八门，*Tair* 、*Redis* 、*Memcache* 、*EhCache* 、*ConcurrentHashMap* 、*HashTable* 等均可以用来实现缓存。
* 缓存使用方式无非就是两种，与业务代码强绑定or与业务代码解耦。借鉴Spring Cache中 **AOP + Annotation** 的思想，同时使用**自动加载机制** 来实现数据“**常驻内存**，提高性能。
* 实现基于aspectj 的AOP，代码入口为[AspectjAopInterceptor](../AspectjAopInterceptor.java "AspectjAopInterceptor") 

### [设计思想及原理](./doc/idea.md)

### [使用方法](./doc/use.md)

### [注解（Annotation）说明](./doc/annotations.md)

### [分布式锁支持](./doc/lock.md)

### [表达式的应用](./doc/script.md)

### [缓存删除](./doc/deleteCache.md)

### [注意事项](./doc/warning.md)

### [与Spring Cache的区别](./doc/SpringCache.md)

### [最佳实战](./doc/suggest.md)

### spring工程接入参考

  ```
    <parent>
        <groupId>com.github.jiahaowen</groupId>
        <artifactId>spring-assistant-component</artifactId>
        <version>1.0</version>
    </parent>
    
    
    <dependency>
        <groupId>com.github.jiahaowen</groupId>
        <artifactId>spring-assistant-component-cache</artifactId>
    </dependency>

  ```
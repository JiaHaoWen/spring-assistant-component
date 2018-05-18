# spring-assistant-component

* Technical components accumulated in daily work.
* 日常工作中沉淀的一些技术组件，可作为辅助功能快速应用，包含下面内容:
   * 缓存
   * 并发
   * 幂等
   * 服务迁移
   * 通用工具
   * 对象比对&&数据校验

* 接入maven依赖

  ```
    <parent>
        <groupId>com.github.jiahaowen</groupId>
        <artifactId>spring-assistant-component</artifactId>
        <version>1.0</version>
    </parent>
    
    <dependencyManagement>
      <dependencies>
        <dependency>
          <groupId>com.github.jiahaowen</groupId>
          <artifactId>spring-assistant-component-cache</artifactId>
        </dependency>
        <dependency>
          <groupId>com.github.jiahaowen</groupId>
          <artifactId>spring-assistant-component-cache-spring</artifactId>
        </dependency> 
        <dependency>
          <groupId>com.github.jiahaowen</groupId>
          <artifactId>spring-assistant-component-concurrent</artifactId>
        </dependency>
        <dependency>
          <groupId>com.github.jiahaowen</groupId>
          <artifactId>spring-assistant-component-idempotent</artifactId>
        </dependency>                       
        <dependency>
          <groupId>com.github.jiahaowen</groupId>
          <artifactId>spring-assistant-component-migration</artifactId>
        </dependency>
        <dependency>
          <groupId>com.github.jiahaowen</groupId>
          <artifactId>spring-assistant-component-util</artifactId>
        </dependency>        
      </dependencies>
    <dependencyManagement>
  ```
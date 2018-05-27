### spring-assistant-component-rule

### 提供基于groovy的简易规则引擎实现

   * 规则最终存储在物理DB中，物理表结构可以参考 RuleScriptSimpleDO.java
   * 存储在物理表中的规则，需要通过服务进行访问。暴露的访问接口为RuleScriptSimpleDAO.java，使用时需要实现该接口(或者是java实现，或者是mybatis中直接使用xml文件实现)
   * 总入口为RuleRunnerBuilder.java、RuleRunner.java，使用时大致使用下面的模式：
   
   ```
      @Autowired private RuleRunnerBuilder ruleRunnerBuilder;

      public void excute(){
      
          RuleRunner runner = ruleRunnerBuilder.buildRuleRunner();
          
          Object result = runner.execute("ruleName",Maps.newHashMap());
      }
      
   ```
   
   * 几个核心概念
     
     * 规则名称
     * 规则脚本
     * 规则执行上下文
     
### maven依赖

  ```
    <parent>
        <groupId>com.github.jiahaowen</groupId>
        <artifactId>spring-assistant-component</artifactId>
        <version>1.0</version>
    </parent>
    
    
    <dependency>
        <groupId>com.github.jiahaowen</groupId>
        <artifactId>spring-assistant-component-rule</artifactId>
    </dependency>
    
 ```
 
### 入口请参考 RuleRunnerBuilder.java、RuleRunner.java
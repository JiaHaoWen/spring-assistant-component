### 一、简介
  + 说明：为业务方提供幂等性功能
    
### 三：功能
   + 以注解的方式提供幂等性功能(配置比较麻烦，但是对代码无入侵)
   + 以编程的方式提供幂等性功能(对代码有入侵，但是相对比较简单)
         
### 四：实现原理
   1. 将对于不同的业务，采用`previous`+业务操作内容特性生成一个唯一标识`trans_contents`，然后对唯一标识进行加密生成全局ID作为`key`
   2. 在`redis`中判断`key`是否存在，如果存在则说明已经操作过了，然后直接返回，如果不存在则将`key`设置到`redis`中
   3. 业务方进行操作完毕后，检查是否操作成功，如果成功则直接返回，否则删除`key`
   4. 在业务操作时，可能会有超时或者主机宕机，所以我们还需要引入一个超时机制，如果超过这个时间，则`key`将被移除，且允许下一个请求进行同等操作

### 五：demo
   + 以注解的方式实现当前业务幂等性
   
            @Idempotent(previous = "uniqueKey", expiredTime = 20, returnType = String.class, returnValue = "test")
            public void idempotentMethod(@IdempotentParam(value = {"testA.testB.name", "testA.testB.age"}) ObjectTest objectTest, @IdempotentParam String haha) {
        
            }
                
            public class ObjectTest {
                private TestA testA;
            
                public TestA getTestA() {
                    return new TestA();
                }
            
                class TestA {
                    private TestB testB;
                    public TestB getTestB() {
                        return new TestB();
                    }
                }
            
                class TestB {
                    private String name = "wodiu";
            
                    public String getName() {
                        return this.name;
                    }
                }
            }
   + `Idempotent`:标注当前功能需要实现幂等性
       + `previous`:业务标识，用于区别不同的业务
       + `expiredTime`:过期时间，在这个时间段内，不能进行重复性的操作
       + `returnType`:返回类型，用于发现重复操作后，直接返回指定的数据类型(详情请查看API)
       + `returnValue`:返回值，用于发现重复操作后，默认返回数据类型的值(详情请查看API)
   + `IdempotentParam`:标注当前参数是否参与唯一值计算，可以是指定某个基本类型，或者是一个对象类型，
                              最终生成的唯一值是以:`Idempotent.previous`+`IdempotentParam`标注的值一起计算出来的
                              
   + 编程方式实现幂等性(个性化设置唯一值和返回值)： 
   
           public String businessWithinIdepotent() {
               String key = "uniqueKey";
               if (!IdempotentOperateUtil.proceed(key, 20)) {
                   return null;
               }
               try {
                   // do business code within idempotent
                   return "success";
               } catch (Throwable throwable) {
                   // 如果业务操作失败后，则需要执行回滚操作，防止后续相同的业务无法操作
                   IdempotentOperateUtil.callBack(key);
                   throw throwable;
               }
           }
               
### 六：引用：
   + [设计强大且可预测的幂等性API](https://stripe.com/blog/idempotency)
   + [安全的实现幂等性操作](https://developer.salesforce.com/blogs/engineering/2013/01/implementing-idempotent-operations-with-salesforce.html)
   + [分布式系统互斥性与幂等性问题的分析与解决](https://tech.meituan.com/distributed-system-mutually-exclusive-idempotence-cerberus-gtis.html)       
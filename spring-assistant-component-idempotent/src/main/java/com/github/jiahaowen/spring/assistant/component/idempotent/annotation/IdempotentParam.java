package com.github.jiahaowen.spring.assistant.component.idempotent.annotation;

import java.lang.annotation.*;

/**
 * 生成幂等性 key 策略
 *
 * <p>example：
 *
 * <pre>{@code
 * public class Order {
 *     private Custom custom;
 *     public Custom getCustom() {
 *         return this.custom;
 *     }
 * }
 * public class Custom {
 *     private String name;
 *     public String getName() {
 *         return this.name;
 *     }
 * }
 *
 * public void run(@IdempotentParam(value("custom.name") Order order, @IdempotentParam int age) {
 *
 * }
 * }</pre>
 *
 * <p>那么最终参与生成的key是包含custom.name.value和age.previous
 *
 * @author jiahaowen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@Inherited
public @interface IdempotentParam {
    /**
     * 参与生成幂等性key的value值
     *
     * <p>如果某个对象是值对象(primitive type or primitive wrapper type or String )则可以不用指定value
     *
     * <p>如果value是对象，可以直接用property表达式获取对象的某个值
     *
     * @return
     */
    String[] value() default "";
}

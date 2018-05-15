package com.github.jiahaowen.spring.assistant.component.migration.abtest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AB Testing测试方法
 *
 * <p>基于 约定大于配置， 两个方法都是基于同一个接口实现，入参&出参数都是一样的配置
 *
 * @author jiahaowen
 * @version $Id: ABTest.java, v 0.1 16/11/18 下午5:23 jiahaowen Exp $
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ABTest {
    /**
     * 替代方法，若为空。默认查找xxxB方法
     *
     * @return
     */
    String methodName() default "";

    /**
     * 分离器
     *
     * @return
     */
    String splitter() default "baseDRMSplitter";

    /**
     * 校验器
     *
     * @return
     */
    String checker() default "defaultChecker";
}

/** WaCai Inc. Copyright (c) 2009-2018 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.control.advisor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在调用时触发
 *
 * @author chuanmu
 * @since 2018/5/15
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Trigger {}

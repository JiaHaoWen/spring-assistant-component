/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.concurrent;

import com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent.TestApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author chuanmu
 * @since 2017/11/20
 */
@SpringBootTest(classes = TestApp.class)
public abstract class ConcurrentComponentTestBase extends AbstractTestNGSpringContextTests {}

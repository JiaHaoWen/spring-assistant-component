/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.concurrent;

import com.github.jiahaowen.spring.assistant.component.concurrent.internal.config.SpringAssistantComponentConcurrentConfig;
import com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent.TestBizExecService;
import com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent.models.Person;
import com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent.models.TestResult;
import com.github.jiahaowen.spring.assistant.component.util.common.result.BatchResult;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.CollectionUtils;
import org.testng.annotations.Test;

/**
 * 并发组件测试
 *
 * @author chuanmu
 * @since 2017/11/20
 */
@ContextConfiguration(classes = {SpringAssistantComponentConcurrentConfig.class})
public class ConcurrentComponentTest extends ConcurrentComponentTestBase {

    /** 业务逻辑模拟类 */
    @Autowired
    @Qualifier("testBizExecService")
    private TestBizExecService testBizExecService;

    /** 并发组件 */
    @Autowired
    private ConcurrentComponent concurrentComponentWithResult;

    @Test
    public void test20Inputs() {
        // 模拟20个请求，一次性丢入组件中进行处理
        List<Person> list = Lists.newArrayList();
        for (int i = 1; i <= 20; i++) {
            Person person =
                    new Person(Integer.toString(i), Integer.toString(i), Integer.toString(i));
            list.add(person);
        }

        // 将20个请求以及业务逻辑模拟类同步丢入并发组件中
        BatchResult<TestResult> batchResult =
                concurrentComponentWithResult.execute(list, testBizExecService);
        // 20个请求必须全部正确
        Assert.assertTrue(batchResult.isSuccess());

        // 确认没有错误结果
        Assert.assertTrue(CollectionUtils.isEmpty(batchResult.getFailedResultObj()));

        // 确认正确结果
        Assert.assertTrue(!CollectionUtils.isEmpty(batchResult.getSucResultObj()));
        // 确认正确结果为20个
        Assert.assertEquals(batchResult.getSucResultObj().size(), 20);
    }

    @Test
    public void test60Inputs() {
        try {
            // 模拟60个请求，一次性丢入组件中进行处理
            List<Person> list = Lists.newArrayList();
            for (int i = 1; i <= 60; i++) {
                Person person =
                        new Person(Integer.toString(i), Integer.toString(i), Integer.toString(i));
                list.add(person);
            }

            // 将60个请求以及业务逻辑模拟类同步丢入并发组件中
            concurrentComponentWithResult.execute(list, testBizExecService);

        } catch (Throwable t) {
            // 数量大于50个,直接抛出异常
            Assert.assertFalse(Boolean.FALSE);
        }
    }

    @Test
    public void testEmptyInputs() {
        try {
            concurrentComponentWithResult.execute(Lists.newArrayList(), testBizExecService);
        } catch (Throwable t) {
            // 输入为空，直接抛出异常
            Assert.assertFalse(Boolean.FALSE);
        }
    }

    @Test
    public void testDuplicateInputs() {
        try {
            // 模拟20个请求，一次性丢入组件中进行处理
            List<Person> list = Lists.newArrayList();
            for (int i = 1; i <= 20; i++) {
                Person person =
                        new Person(Integer.toString(i), Integer.toString(i), Integer.toString(i));
                list.add(person);
            }

            // 添加重复请求
            Person person = new Person("20", "20", "20");
            list.add(person);

            concurrentComponentWithResult.execute(list, testBizExecService);
        } catch (Throwable t) {
            // 存在相同请求，直接抛出异常
            Assert.assertFalse(Boolean.FALSE);
        }
    }

    @Test
    public void testSingleRequestWrong() {
        List<Person> list = Lists.newArrayList();
        for (int i = 1; i <= 20; i++) {
            Person person =
                    new Person(Integer.toString(i), Integer.toString(i), Integer.toString(i));
            list.add(person);
        }

        // 模拟单次错误请求
        Person person =
                new Person("singleRequestWrong", "singleRequestWrong", "singleRequestWrong");
        list.add(person);

        // 将请求以及业务逻辑模拟类同步丢入并发组件中
        BatchResult<TestResult> batchResult =
                concurrentComponentWithResult.execute(list, testBizExecService);

        // 整体结果为false
        Assert.assertFalse(batchResult.isSuccess());

        // 确认有错误结果
        Assert.assertTrue(!CollectionUtils.isEmpty(batchResult.getFailedResultObj()));
        // 确认错误结果为1个
        Assert.assertEquals(batchResult.getFailedResultObj().size(), 1);

        // 确认正确结果
        Assert.assertTrue(!CollectionUtils.isEmpty(batchResult.getSucResultObj()));
        // 确认正确结果为20个
        Assert.assertEquals(batchResult.getSucResultObj().size(), 20);
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.core;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.MigrationTestBase;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.core.client.Client;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.core.client.impl.ClientImpl;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.config.ABTestConfig;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class InterceptServiceTest extends MigrationTestBase {

    @Autowired private Client client;

    @Autowired private ABTestConfig aBTestConfig;

    /**
     * 异步
     *
     * @throws Throwable
     */
    @Test
    public void testQuery() throws Throwable {
        // 打开校验，返回A逻辑结果
        aBTestConfig.updateABTestConfig("0:3:0:query:10000:10000;0:3:0:query2:10000:10000");
        final String i = client.query("a", 1);
        System.out.println(i);
        Assert.assertEquals(ClientImpl.class.getName() + ".query", i);

        final String i2 = client.query2("a", 1);
        System.out.println(i2);
        Assert.assertEquals(ClientImpl.class.getName() + ".query2", i2);

        // 关闭校验,返回B逻辑结果
        aBTestConfig.updateABTestConfig("0:3:0:query:10000:0;0:3:0:query2:10000:0");
        final String i3 = client.query("a", 1);
        System.out.println(i3);
        Assert.assertEquals(ClientImpl.class.getName() + ".queryB", i3);

        final String i4 = client.query2("a", 1);
        System.out.println(i4);
        Assert.assertEquals(ClientImpl.class.getName() + ".query2B", i4);
    }

    @Test
    public void testQuery2() throws Throwable {
        // 打开校验，返回A逻辑结果
        aBTestConfig.updateABTestConfig("0:3:0:query:10000:10000;0:3:0:query2:10000:10000");
        final String i = client.query("a", 1);
        System.out.println(i);
        Assert.assertEquals(ClientImpl.class.getName() + ".query", i);

        final String i2 = client.query2("a", 1);
        System.out.println(i2);
        Assert.assertEquals(ClientImpl.class.getName() + ".query2", i2);
    }

    /**
     * 同步
     *
     * @throws Throwable
     */
    @Test
    public void testQuery3() throws Throwable {
        // 打开校验，返回A逻辑结果
        aBTestConfig.updateABTestConfig("0:3:1:query:10000:10000;0:3:1:query2:10000:10000");
        final String i = client.query("a", 1);
        System.out.println(i);
        Assert.assertEquals(ClientImpl.class.getName() + ".query", i);

        final String i2 = client.query2("a", 1);
        System.out.println(i2);
        Assert.assertEquals(ClientImpl.class.getName() + ".query2", i2);

        // 关闭校验,返回B逻辑结果
        aBTestConfig.updateABTestConfig("0:3:1:query:10000:0;0:3:1:query2:10000:0");
        final String i3 = client.query("a", 1);
        System.out.println(i3);
        Assert.assertEquals(ClientImpl.class.getName() + ".queryB", i3);

        final String i4 = client.query2("a", 1);
        System.out.println(i4);
        Assert.assertEquals(ClientImpl.class.getName() + ".query2B", i4);
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.checker.impl;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.MigrationTestBase;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker.impl.DefaultChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultCheckerTest extends MigrationTestBase {

    @Autowired private DefaultChecker defaultChecker;

    @Test
    public void doCheck() throws Exception {

        Assert.assertNotNull(defaultChecker.doCheck(1, 1L));
        Assert.assertNotNull(defaultChecker.doCheck(true, 1L));
        Assert.assertNotNull(defaultChecker.doCheck("1L", 1L));
        Assert.assertNotNull(defaultChecker.doCheck(2.0D, 1L));
        Assert.assertNotNull(defaultChecker.doCheck(new boolean[] {}, 1L));
        Assert.assertNotNull(defaultChecker.doCheck(new int[] {}, 1L));
        Assert.assertNotNull(defaultChecker.doCheck(new float[] {}, 1L));
        Assert.assertNotNull(defaultChecker.doCheck(new Integer[] {}, 1L));
        Assert.assertNotNull(defaultChecker.doCheck(new Float[] {}, 1L));
    }
}

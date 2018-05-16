package com.github.jiahaowen.spring.assistant.component.migration.abtest.common;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.MigrationTestBase;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.RunModeEnum;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RunModeEnumTest extends MigrationTestBase {
    @Test
    public void getMsg() throws Exception {
        Assert.assertEquals(RunModeEnum.OFF.getMsg(), "关闭");
        Assert.assertEquals(RunModeEnum.READ.getMsg(), "读");
        Assert.assertEquals(RunModeEnum.WRITE.getMsg(), "写");
        Assert.assertEquals(RunModeEnum.ALL.getMsg(), "读写");
    }

    @Test
    public void getCode() throws Exception {
        Assert.assertEquals(RunModeEnum.OFF.getCode(), 0);
        Assert.assertEquals(RunModeEnum.READ.getCode(), 1);
        Assert.assertEquals(RunModeEnum.WRITE.getCode(), 2);
        Assert.assertEquals(RunModeEnum.ALL.getCode(), 3);
    }

    @Test
    public void isEnable() throws Exception {
        Assert.assertTrue(RunModeEnum.isMatch(0, RunModeEnum.OFF));
        Assert.assertTrue(RunModeEnum.isMatch(1, RunModeEnum.READ));
        Assert.assertTrue(RunModeEnum.isMatch(2, RunModeEnum.WRITE));
        Assert.assertTrue(RunModeEnum.isMatch(3, RunModeEnum.ALL));

        Assert.assertFalse(RunModeEnum.isMatch(1, RunModeEnum.OFF));
        Assert.assertFalse(RunModeEnum.isMatch(1, RunModeEnum.WRITE));
        Assert.assertFalse(RunModeEnum.isMatch(2, RunModeEnum.READ));
        Assert.assertFalse(RunModeEnum.isMatch(3, RunModeEnum.OFF));
        Assert.assertFalse(RunModeEnum.isMatch(0, RunModeEnum.ALL));
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.common.util;

import static com.github.jiahaowen.spring.assistant.component.migration.abtest.common.models.ABTestConstants.EXTREME_POSITION;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.common.enums.RunModeEnum;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * AB Test 工具类
 *
 * @author jiahaowen
 * @version $Id: ABTestUtil.java, v 0.1 2016-11-23 上午10:33 jiahaowen Exp $
 */
public class ABTestUtil {

    /**
     * @param frequency 0~10000
     * @return 是否命中 @See ABTestConstants.EXTREME_POSITION
     */
    public static boolean isRandomHit(int frequency) {
        if (frequency >= EXTREME_POSITION) {
            return true;
        } else if (frequency <= 0) {
            return false;
        }
        return RandomUtils.nextInt(0, EXTREME_POSITION) < frequency;
    }

    /**
     * 存在事务认为是只写
     *
     * @return
     */
    public static RunModeEnum currentMode() {
        return TransactionSynchronizationManager.isSynchronizationActive()
                ? RunModeEnum.WRITE
                : RunModeEnum.READ;
    }
}

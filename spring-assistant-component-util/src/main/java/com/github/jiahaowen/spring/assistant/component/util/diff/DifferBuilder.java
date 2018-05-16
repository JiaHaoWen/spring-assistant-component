package com.github.jiahaowen.spring.assistant.component.util.diff;

import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.Configuration;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.Differ;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DiffException;
import java.util.List;
import javax.annotation.concurrent.ThreadSafe;

/**
 * <b>通用对象差异点比较工具</b>
 *
 * <p>使用示例:
 *
 * <p>boolean isEquals = DifferBuilder.build().isEqual(object1, object2);
 *
 * <p>Assert.assertTrue(isEquals);
 *
 * @author jiahaowen.jhw
 * @version $Id: DifferBuilder.java, v 0.1 2016-10-31 下午8:31 jiahaowen.jhw Exp $
 */
@ThreadSafe
public class DifferBuilder {

    /**
     * 初始化
     *
     * @return
     */
    public static DifferBuilder build() {
        return new DifferBuilder();
    }

    /**
     * 初始化Differ
     *
     * @return
     */
    public static Differ differ() {
        return new Differ();
    }

    /**
     * 初始化Differ
     *
     * @return
     */
    public static Differ differ(Configuration configuration) {
        return new Differ(configuration);
    }

    /**
     * 默认策略比较两个对象是否相同
     *
     * <p>返回为true,则相等 返回为false,则不同
     *
     * @param o1
     * @param o2
     * @return
     */
    public boolean isEqual(Object o1, Object o2) throws DiffException {
        return differ().isObjectEqualBaseMyers(o1, o2);
    }

    /**
     * 默认策略比较两个对象是否相同,返回不同点
     *
     * <p>返回为true,则相等 返回为false,则不同
     *
     * @param o1
     * @param o2
     * @return
     */
    public List<CompareResult> compare(Object o1, Object o2) throws DiffException {
        return differ().compareObjectBaseMyers(o1, o2);
    }

    public List<CompareResult> quickCompare(Object o1, Object o2) throws DiffException {
        return differ().quickCompareByMyers(o1, o2);
    }
}

package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm;

import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.Patch;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import java.util.List;

/**
 * 比较两个List<String>的算法接口,返回差异信息
 *
 * @author jiahaowen.jhw
 * @version $Id: DiffAlgorithm.java, v 0.1 2016-10-30 下午10:01 jiahaowen.jhw Exp $
 */
public interface DiffAlgorithm {

    /** 计算两个字符串序列之间的不同点,返回{@link Patch}描述差异点. */
    List<CompareResult> diff(List<String> original, List<String> revised);

    /**
     * 计算两个字符串序列之间是否相同
     *
     * @param original
     * @param revised
     * @return
     */
    boolean isEqual(List<String> original, List<String> revised);

    List<CompareResult> diffUnsort(final List<String> original, final List<String> revised);
}

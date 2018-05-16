package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.impl;

import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.DiffAlgorithm;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.ChangeDelta;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.Chunk;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.DeleteDelta;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.Delta;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.DiffNode;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.InsertDelta;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.Patch;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.PathNode;
import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.Snake;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DifferentiationFailedException;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * Gene Myers differencing algorithm,比较两个String序列.
 *
 * <p>核心思想:编辑边的长度为D,寻找最小的D,使时间复杂度和空间复杂度都为O(ND).主要是在对角线上进行操作.
 *
 * <p>论文参见 <a href "https://neil.fraser.name/software/diff_match_patch/myers.pdf">
 *
 * <p>算法简要分析 <a href "http://gitlab.alipay-inc.com/app_release/arcore/issues/183">
 *
 * <p>基本概念:
 *
 * <p>1.Graph：主要保存两字符串的编辑信息，路径信息，公共子序列信息
 *
 * <p>2.Snake: 由连续匹配点形成的对角线，称为Snake
 *
 * <p>3.Node Edit Type：变化,删除,插入字符，使得字符串完全匹配，D=Delete, I=Insert,C=CHANGE
 *
 * <p>4.Shortest Trace：两点之间的最短路径，即Snake的个数和长度最长(同理Edit Scripts的数量最少)。
 * 该路径满足花费最小的编辑，使两字符串匹配。同时,该路径中的Snake即为最长公共子序列
 *
 * @author jiahaowen.jhw
 * @version $Id: MyersDiff.java, v 0.1 2016-10-30 下午10:00 jiahaowen.jhw Exp $
 */
public class MyersDiff implements DiffAlgorithm {

    /** 比对结果转义 */
    public static List<CompareResult> constructCompareResult(final Patch path) {

        List<CompareResult> compareResults = Lists.newArrayList();

        List<Delta> deltas = path.getDeltas();
        for (Delta delta : deltas) {

            List<String> original = null;
            List<String> revised = null;

            if (delta.getOriginal() != null) {
                original = delta.getOriginal().getLines();
            }

            if (delta.getRevised() != null) {
                revised = delta.getRevised().getLines();
            }

            CompareResult compareResult = new CompareResult(original, revised);
            compareResults.add(compareResult);
        }
        return compareResults;
    }

    /** 比较过程中有错误时,返回空的Patch */
    @Override
    public List<CompareResult> diff(final List<String> original, final List<String> revised) {
        if (original == null) {
            throw new IllegalArgumentException("original list must not be null");
        }
        if (revised == null) {
            throw new IllegalArgumentException("revised list must not be null");
        }
        PathNode path;
        try {
            Collections.sort(original);
            Collections.sort(revised);
            path = buildPath(original, revised);
            return buildRevision(path, original, revised);
        } catch (DifferentiationFailedException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    @Override
    public List<CompareResult> diffUnsort(final List<String> original, final List<String> revised) {
        if (original == null) {
            throw new IllegalArgumentException("original list must not be null");
        }
        if (revised == null) {
            throw new IllegalArgumentException("revised list must not be null");
        }
        PathNode path;
        try {
            path = buildPath(original, revised);
            return buildRevision(path, original, revised);
        } catch (DifferentiationFailedException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    /**
     * 计算两个字符串序列之间是否相同
     *
     * @param original
     * @param revised
     * @return
     */
    @Override
    public boolean isEqual(List<String> original, List<String> revised) {
        List<CompareResult> results = diff(original, revised);

        if (CollectionUtils.isEmpty(results)) {
            return true;
        }

        return false;
    }

    /** 根据Gene Myers differencing algorithm,计算两个字符串序列中的最小差异路径 */
    public PathNode buildPath(final List<String> orig, final List<String> rev)
            throws DifferentiationFailedException {
        if (orig == null) throw new IllegalArgumentException("original sequence is null");
        if (rev == null) throw new IllegalArgumentException("revised sequence is null");

        final int N = orig.size();
        final int M = rev.size();

        final int MAX = N + M + 1;
        final int size = 1 + 2 * MAX;
        final int middle = size / 2;
        final PathNode diagonal[] = new PathNode[size];

        diagonal[middle + 1] = new Snake(0, -1, null);
        for (int d = 0; d < MAX; d++) {
            for (int k = -d; k <= d; k += 2) {
                final int kmiddle = middle + k;
                final int kplus = kmiddle + 1;
                final int kminus = kmiddle - 1;
                PathNode prev = null;

                int i;
                if ((k == -d) || (k != d && diagonal[kminus].i < diagonal[kplus].i)) {
                    i = diagonal[kplus].i;
                    prev = diagonal[kplus];
                } else {
                    i = diagonal[kminus].i + 1;
                    prev = diagonal[kminus];
                }

                diagonal[kminus] = null;

                int j = i - k;

                PathNode node = new DiffNode(i, j, prev);

                // 字符串序列从0开始计数,算法从1开始计数
                while (i < N && j < M && equals(orig.get(i), rev.get(j))) {
                    i++;
                    j++;
                }
                if (i > node.i) node = new Snake(i, j, node);

                diagonal[kmiddle] = node;

                if (i >= N && j >= M) {
                    return diagonal[kmiddle];
                }
            }
            diagonal[middle + d - 1] = null;
        }
        throw new DifferentiationFailedException("could not find a diff path");
    }

    /**
     * 判断两个String是否相等,默认为jdk中的方法
     *
     * @param orig
     * @param rev
     * @return
     */
    private boolean equals(String orig, String rev) {
        return orig.equals(rev);
    }

    /** 构造差异信息 */
    public List<CompareResult> buildRevision(PathNode path, List<String> orig, List<String> rev) {
        // 校验
        if (path == null) throw new IllegalArgumentException("path is null");
        if (orig == null) throw new IllegalArgumentException("original sequence is null");
        if (rev == null) throw new IllegalArgumentException("revised sequence is null");

        Patch patch = new Patch();
        if (path.isSnake()) path = path.prev;
        while (path != null && path.prev != null && path.prev.j >= 0) {
            if (path.isSnake())
                throw new IllegalStateException("bad diffpath: found snake when looking for diff");
            int i = path.i;
            int j = path.j;

            path = path.prev;
            int ianchor = path.i;
            int janchor = path.j;

            Chunk original = new Chunk(ianchor, copyOfRange(orig, ianchor, i));
            Chunk revised = new Chunk(janchor, copyOfRange(rev, janchor, j));
            Delta delta = null;
            if (original.size() == 0 && revised.size() != 0) {
                delta = new InsertDelta(original, revised);
            } else if (original.size() > 0 && revised.size() == 0) {
                delta = new DeleteDelta(original, revised);
            } else {
                delta = new ChangeDelta(original, revised);
            }

            patch.addDelta(delta);
            if (path.isSnake()) path = path.prev;
        }
        return constructCompareResult(patch);
    }

    /** 从字符串序列中拷贝指定位置的子序列 */
    private List<String> copyOfRange(
            final List<String> original, final int fromIndex, final int to) {
        return new ArrayList<String>(original.subList(fromIndex, to));
    }
}

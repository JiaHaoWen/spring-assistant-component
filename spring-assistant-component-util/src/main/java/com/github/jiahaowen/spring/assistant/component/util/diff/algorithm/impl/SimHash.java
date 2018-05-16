package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.impl;

import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.DiffAlgorithm;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * simhash判断字符串是否相等
 *
 * @author jiahaowen.jhw
 * @version $Id: SimHash.java, v 0.1 2016-11-06 下午11:07 jiahaowen.jhw Exp $
 */
public class SimHash implements DiffAlgorithm {

    /**
     * Generate 64 bit simhash for a string
     *
     * @param s
     * @return
     */
    public static long simHash64(String s) {
        long result = 0;
        int[] bitVector = new int[64];

        String[] words = s.split("[\\s()\\-\\/]+");
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            long hash = fvnHash64(word);
            for (int i = 0; i < bitVector.length; i++) {
                bitVector[i] += (hash & 1) == 1 ? 1 : -1;
                hash = hash >> 1;
            }
        }

        for (int i = 0; i < bitVector.length; i++) {
            result = result << 1;
            if (bitVector[i] > 0) {
                result += 1;
            }
        }

        return result;
    }

    /**
     * 计算海明距离
     *
     * @param a
     * @param b
     * @return
     */
    public static int hammingDistance(long a, long b) {
        int dist = 0;
        a = a ^ b;
        while (a != 0) {
            a &= a - 1;
            dist++;
        }
        return dist;
    }

    /**
     * 为字符串生成64位 FVN 哈希
     *
     * @param s
     * @return
     */
    public static long fvnHash64(String s) {
        long basis = 0xcbf29ce484222325L;
        long prime = 0x100000001b3L;
        for (int i = 0; i < s.length(); i++) {
            basis ^= s.charAt(i);
            basis *= prime;
        }
        return basis;
    }

    /**
     * simhash计算字符串序列是否相等
     *
     * @param original
     * @param revised
     */
    @Override
    public boolean isEqual(List<String> original, List<String> revised) {
        List<String> equalLists = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(original) && !CollectionUtils.isEmpty(revised)) {
            for (String s1 : original) {
                long l1 = simHash64(s1);
                for (String s2 : revised) {
                    long l2 = simHash64(s2);
                    if (hammingDistance(l1, l2) == 0) {
                        equalLists.add(s1);
                    }
                }
            }

            if (!CollectionUtils.isEmpty(equalLists) && (original.size() == equalLists.size())) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<CompareResult> diffUnsort(List<String> original, List<String> revised) {
        return diff(original, revised);
    }

    /**
     * 计算字符串序列差异值
     *
     * @param original
     * @param revised
     * @return
     */
    @Override
    public List<CompareResult> diff(List<String> original, List<String> revised) {

        List<CompareResult> results = Lists.newArrayList();

        int[] originalEqualIndex = new int[original.size()];

        for (int i = 0; i < original.size(); i++) {
            long l1 = simHash64(original.get(i));
            for (int j = 0; j < revised.size(); j++) {
                long l2 = simHash64(revised.get(j));
                if (hammingDistance(l1, l2) == 0) {
                    originalEqualIndex[i] = i;
                }
            }
        }

        List<Integer> missArray = getMissingNos(originalEqualIndex);

        if (!CollectionUtils.isEmpty(missArray)) {
            for (int miss : missArray) {
                CompareResult result =
                        new CompareResult(
                                Lists.newArrayList(original.get(miss)),
                                Lists.newArrayList(revised.get(miss)));
                results.add(result);
            }
        }

        return results;
    }

    /**
     * 查找缺失的数据
     *
     * @param a
     * @return
     */
    private List<Integer> getMissingNos(int a[]) {

        List<Integer> missList = Lists.newArrayList();

        if (a[0] != 0) {
            missList.add(0);
        }

        for (int i = 1; i < a.length; i++) {
            while (a[a[i]] != a[i]) {
                int t = a[i];
                a[i] = a[t];
                a[t] = t;
            }
        }

        for (int i = 1; i < a.length; i++) {
            if (a[i] != i) {
                missList.add(i);
            }
        }

        return missList;
    }
}

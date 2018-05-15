package com.github.jiahaowen.spring.assistant.component.migration.diff.differ;

import com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.DiffAlgorithm;
import com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.impl.MyersDiff;
import com.github.jiahaowen.spring.assistant.component.migration.diff.exception.DiffException;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.linewriter.LineWriter;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.linewriter.RootLineWriter;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.PathInclusionChecker;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.PropertyInclusionChecker;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.SerializerFactory;
import com.google.common.collect.Lists;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.ThreadSafe;

/**
 * 对象差异比对工具
 *
 * @author jiahaowen.jhw
 * @version $Id: Differ.java, v 0.1 2016-10-30 下午9:23 jiahaowen.jhw Exp $
 */
@ThreadSafe
public final class Differ {

    /** 自定义配置 */
    private final Configuration configuration;

    /** 序列化 */
    private final LineWriter lineWriter;

    /** 构造函数 */
    public Differ() {
        this.lineWriter = createDefaultRootLineWriter();
        this.configuration = createDefaultConfiguration();
    }

    /**
     * 构造函数
     *
     * @param configuration
     */
    public Differ(Configuration configuration) {
        this.lineWriter = createRootLineWriter(configuration);
        this.configuration = configuration;
    }

    /** 采用自定义的差异比较算法,比较两个字符串序列之间的差异点 */
    public List<CompareResult> diff(
            List<String> original, List<String> revised, DiffAlgorithm algorithm) {
        if (original == null) {
            throw new IllegalArgumentException("original must not be null");
        }
        if (revised == null) {
            throw new IllegalArgumentException("revised must not be null");
        }
        if (algorithm == null) {
            throw new IllegalArgumentException("algorithm must not be null");
        }
        return algorithm.diff(original, revised);
    }

    /**
     * 自定义算法判断对象是否相同
     *
     * @param base
     * @param working
     * @param algorithm
     * @return
     * @throws DiffException
     */
    public List<CompareResult> customAlgorithmDiff(
            final Object base, final Object working, DiffAlgorithm algorithm) throws DiffException {

        if (base == null && working != null) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("比对时的基准对象为空"), Lists.newArrayList("比对时需要比对的对象不为空")));
        }

        if (base != null && working == null) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("比对时的基准对象不为空"), Lists.newArrayList("比对时需要比对的对象为空")));
        }

        if (base == null && working == null) {
            return Lists.newArrayList();
        }

        if (base.equals(working)) {
            return Lists.newArrayList();
        }

        if (!base.getClass().getName().equalsIgnoreCase(working.getClass().getName())) {
            if (Iterable.class.isAssignableFrom(base.getClass())
                    && Iterable.class.isAssignableFrom(working.getClass())) {
            } else if (Map.class.isAssignableFrom(base.getClass())
                    && Map.class.isAssignableFrom(working.getClass())) {
            } else {
                return Lists.newArrayList(
                        new CompareResult(
                                Lists.newArrayList("比对时的基准对象类型为:" + base.getClass().getName()),
                                Lists.newArrayList(
                                        "比对时需要比对的对象类型为:" + working.getClass().getName())));
            }
        }

        return algorithm.diff(serilizerObject(base), serilizerObject(working));
    }

    /** 自定义算法判断对象是否相同 */
    public boolean isEqualsByCustomAlgorithm(
            final Object base, final Object working, DiffAlgorithm algorithm) throws DiffException {

        if (base == null && working != null) {
            return false;
        }

        if (base != null && working == null) {
            return false;
        }

        if (base == null && working == null) {
            return true;
        }

        if (base.equals(working)) {
            return true;
        }

        if (!base.getClass().getName().equalsIgnoreCase(working.getClass().getName())) {
            if (Iterable.class.isAssignableFrom(base.getClass())
                    && Iterable.class.isAssignableFrom(working.getClass())) {
            } else if (Map.class.isAssignableFrom(base.getClass())
                    && Map.class.isAssignableFrom(working.getClass())) {
            } else {
                return false;
            }
        }

        return algorithm.isEqual(serilizerObject(base), serilizerObject(working));
    }

    /**
     * 比较两个对象是否相等
     *
     * @param base
     * @param working
     * @return
     */
    public boolean isObjectEqualBaseMyers(final Object base, final Object working)
            throws DiffException {

        return isEqualsByCustomAlgorithm(base, working, new MyersDiff());
    }

    /**
     * 对象层级关系\值 序列化
     *
     * @param object
     * @return
     * @throws DiffException
     */
    private List<String> serilizerObject(final Object object) throws DiffException {
        String objectClassName = getBeanName(object);
        return lineWriter.write(objectClassName, object);
    }

    public List<CompareResult> quickCompareByMyers(final Object base, final Object working)
            throws DiffException {

        if (base == null && working != null) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("比对时的基准对象为空"), Lists.newArrayList("比对时需要比对的对象不为空")));
        }

        if (base != null && working == null) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("比对时的基准对象不为空"), Lists.newArrayList("比对时需要比对的对象为空")));
        }

        if (base == null && working == null) {
            return Lists.newArrayList();
        }

        if (base.equals(working)) {
            return Lists.newArrayList();
        }

        if (!base.getClass().getName().equalsIgnoreCase(working.getClass().getName())) {
            if (Iterable.class.isAssignableFrom(base.getClass())
                    && Iterable.class.isAssignableFrom(working.getClass())) {
            } else if (Map.class.isAssignableFrom(base.getClass())
                    && Map.class.isAssignableFrom(working.getClass())) {
            } else {
                return Lists.newArrayList(
                        new CompareResult(
                                Lists.newArrayList("比对时的基准对象类型为:" + base.getClass().getName()),
                                Lists.newArrayList(
                                        "比对时需要比对的对象类型为:" + working.getClass().getName())));
            }
        }

        String path = getBeanName(base);

        return lineWriter.write(path, base, working);
    }

    /** 返回对象类型 */
    private String getBeanName(final Object object) {
        return object.getClass().getSimpleName();
    }

    /**
     * 比较两个对象的是否相同,返回不同点
     *
     * @param base
     * @param working
     * @return
     */
    public List<CompareResult> compareObjectBaseMyers(final Object base, final Object working)
            throws DiffException {

        return customAlgorithmDiff(base, working, new MyersDiff());
    }

    /** 序列化根节点 */
    private LineWriter createRootLineWriter(Configuration configuration) {
        return new RootLineWriter(
                new PropertyInclusionChecker(
                        configuration.getExcludedProperties(),
                        configuration.getExcludedClassSimpleNames()),
                new PathInclusionChecker(configuration.getExcludedPaths()),
                new SerializerFactory());
    }

    private LineWriter createDefaultRootLineWriter() {
        return new RootLineWriter(
                new PropertyInclusionChecker(new HashSet<String>(), new HashSet<String>()),
                new PathInclusionChecker(new HashSet<String>()),
                new SerializerFactory());
    }

    private Configuration createDefaultConfiguration() {
        return new Configuration();
    }
}

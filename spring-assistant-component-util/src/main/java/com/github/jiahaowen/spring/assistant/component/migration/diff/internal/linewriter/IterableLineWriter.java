package com.github.jiahaowen.spring.assistant.component.migration.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.impl.MyersDiff;
import com.github.jiahaowen.spring.assistant.component.migration.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.migration.diff.exception.DiffException;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * Iterable类型序列化类
 *
 * @author jiahaowen.jhw
 * @version $Id: IterableLineWriter.java, v 0.1 2016-10-30 下午9:41 jiahaowen.jhw Exp $
 */
public class IterableLineWriter extends TypeSafeCheckableLineWriter<Iterable<Object>> {

    private static final MyersDiff myersDiff = new MyersDiff();
    /** 内部序列化类 */
    private final LineWriter lineWriter;

    /** 构造函数 */
    public IterableLineWriter(final LineWriter lineWriter) {
        this.lineWriter = lineWriter;
    }

    @Override
    public List<String> typeSafeWrite(final String path, final Iterable<Object> iterable)
            throws DiffException {
        final List<String> lines = Lists.newArrayList();
        if (!Iterables.isEmpty(iterable)) {
            for (final Object nestedProperty : iterable) {
                lines.addAll(lineWriter.write(path, nestedProperty));
            }
        }

        return lines;
    }

    @Override
    public boolean applies(final Object value) {
        return value instanceof Iterable;
    }

    @Override
    public List<CompareResult> typeSafeWrite(
            final String path, final Iterable<Object> base, final Iterable<Object> working)
            throws DiffException {

        if (Iterables.size(base) == 0 && Iterables.size(working) == 0) {
            return Lists.newArrayList();
        }

        if (Iterables.size(base) != Iterables.size(working)) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("路径:" + path + "下对象的size为" + Iterables.size(base)),
                            Lists.newArrayList(
                                    "路径:" + path + "下对象的size为" + Iterables.size(working))));
        }

        if (!Iterables.isEmpty(base) && !Iterables.isEmpty(working)) {
            // 对理想的顺序场景,单次循环即可完成
            // 对存在乱序的场景,性能可能会比较耗时
            int index = 0;
            for (final Object baseProperty : base) {
                boolean isEqual = false;
                ArrayList<CompareResult> results = Lists.newArrayList();
                for (final Object workingProperty : working) {

                    final String extendedPath =
                            PathBuilder.extendPathWithIterableIndex(path, index);
                    List<CompareResult> result =
                            lineWriter.write(extendedPath, baseProperty, workingProperty);
                    if (CollectionUtils.isEmpty(result)) {
                        isEqual = true;
                        break;
                    }
                    results.addAll(result);
                }
                if (!isEqual) {
                    /*return Lists
                    .newArrayList(new CompareResult(
                        Lists.newArrayList(
                            "路径:" + path + "下的Iterable类型属性存在不同:" + baseProperty.toString()),
                    null));*/
                    return results;
                }
                index++;
            }
        }
        return Lists.newArrayList();
    }
}

package com.github.jiahaowen.spring.assistant.component.util.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DiffException;
import com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer.PathInclusionChecker;
import com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer.PropertyInclusionChecker;
import com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer.SerializerFactory;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * 根对象序列化类
 *
 * @author jiahaowen.jhw
 * @version $Id: RootLineWriter.java, v 0.1 2016-10-30 下午9:39 jiahaowen.jhw Exp $
 */
public class RootLineWriter implements LineWriter {

    /** 可应用在序列化图中的序列化策略 */
    private final List<CheckableLineWriter> lineWriters;

    /** 属性排除校验 */
    private final PropertyInclusionChecker propertyInclusionChecker;

    /** 指定路径排除校验 */
    private final PathInclusionChecker pathInclusionChecker;

    /** 构造函数 */
    public RootLineWriter(
            final PropertyInclusionChecker propertyInclusionChecker,
            final PathInclusionChecker pathInclusionChecker,
            final SerializerFactory serializerFactory) {
        this.propertyInclusionChecker = propertyInclusionChecker;
        this.pathInclusionChecker = pathInclusionChecker;
        this.lineWriters =
                Lists.newArrayList(
                        new SerializerLineWriter(serializerFactory),
                        new IterableLineWriter(this),
                        new MapLineWriter(this, serializerFactory),
                        new ArrayLineWriter(this),
                        new CustomerLineWriter(
                                this,
                                serializerFactory,
                                propertyInclusionChecker,
                                pathInclusionChecker));
    }

    @Override
    public List<String> write(final String path, final Object value) throws DiffException {

        for (final CheckableLineWriter lineWriter : lineWriters) {
            if (lineWriter.applies(value)) {
                return lineWriter.write(path, value);
            }
        }
        return Lists.newArrayList("暂不支持的类型:" + value.getClass().getSimpleName());
    }

    /**
     * 对象比对
     *
     * @param path
     * @param base
     * @param working @return
     * @throws DiffException
     */
    @Override
    public List<CompareResult> write(String path, Object base, Object working)
            throws DiffException {

        if (base == null && working == null) {
            return Lists.newArrayList();
        }

        if (working == null || base == null) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("路径:" + path + "下对象类型为base:" + base),
                            Lists.newArrayList("路径:" + path + "下对象类型为working:" + working)));
        }

        for (final CheckableLineWriter lineWriter : lineWriters) {
            if (lineWriter.applies(base) && lineWriter.applies(working)) {
                return lineWriter.write(path, base, working);
            } else if (!lineWriter.applies(base) && !lineWriter.applies(working)) {
                continue;
            } else {
                return Lists.newArrayList(
                        new CompareResult(
                                Lists.newArrayList(
                                        "path:"
                                                + path
                                                + ",下的"
                                                + base.getClass().getSimpleName()
                                                + "匹配"
                                                + lineWriter.getClass().getSimpleName()
                                                + "类型有差异"),
                                Lists.newArrayList(
                                        "path:"
                                                + path
                                                + ",下的"
                                                + working.getClass().getSimpleName()
                                                + "匹配"
                                                + lineWriter.getClass().getSimpleName()
                                                + "类型有差异")));
            }
        }

        return Lists.newArrayList(
                new CompareResult(
                        Lists.newArrayList("暂不支持的类型:" + base.getClass().getSimpleName()),
                        Lists.newArrayList("暂不支持的类型:" + base.getClass().getSimpleName())));
    }
}

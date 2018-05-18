package com.github.jiahaowen.spring.assistant.component.util.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.util.common.util.ArrayUtil;
import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DiffException;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * 处理数组类型
 *
 * @author jiahaowen.jhw
 * @version $Id: ArrayLineWriter.java, v 0.1 2016-11-02 下午8:42 jiahaowen.jhw Exp $
 */
public class ArrayLineWriter implements CheckableLineWriter {

    /** 内部序列化类 */
    private final LineWriter lineWriter;

    /** @param lineWriter */
    public ArrayLineWriter(final LineWriter lineWriter) {
        this.lineWriter = lineWriter;
    }

    /**
     * @param value
     * @return
     */
    @Override
    public boolean applies(final Object value) {

        if (value.getClass().isArray()) {
            return true;
        }
        return false;
    }

    /**
     * @param path 路径描述了给定的对象在对象图中的位置.
     * @param value 待序列化的对象
     * @return
     * @throws DiffException
     */
    @Override
    public List<String> write(String path, Object value) throws DiffException {

        final List<String> lines = Lists.newArrayList();
        List<Object> valueList = ArrayUtil.toList(value);
        if (!CollectionUtils.isEmpty(valueList)) {
            for (final Object nestedProperty : valueList) {
                lines.addAll(lineWriter.write(path, nestedProperty));
            }
        }
        // Object[] objectArray = convert2ObjectArray(value);

        //        final List<String> lines = Lists.newArrayList();
        //        if (objectArray.length > 0) {
        //            int i = 0;
        //            for (final Object nestedProperty : objectArray) {
        //                final String extendedPath = PathBuilder.extendPathWithIterableIndex(path,
        // i++);
        //                lines.addAll(lineWriter.write(extendedPath, nestedProperty));
        //            }
        //        }
        return lines;
    }

    /**
     * 对象比对
     *
     * @param path
     * @param base
     * @param working
     */
    @Override
    public List<CompareResult> write(String path, Object base, Object working)
            throws DiffException {
        List<Object> baseLists = ArrayUtil.toList(base);
        List<Object> workingLists = ArrayUtil.toList(working);

        if (baseLists.size() == 0 && workingLists.size() == 0) {
            return Lists.newArrayList();
        }

        if (baseLists.size() != workingLists.size()) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("路径:" + path + "下对象的size为" + baseLists.size()),
                            Lists.newArrayList("路径:" + path + "下对象的size为" + workingLists.size())));
        }

        // 对理想的顺序场景,单次循环即可完成
        // 对存在乱序的场景,性能可能会比较耗时
        for (Object o1 : baseLists) {
            boolean isEqual = false;
            for (Object o2 : workingLists) {
                List<CompareResult> result = lineWriter.write(path, o1, o2);
                if (CollectionUtils.isEmpty(result)) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual) {
                return Lists.newArrayList(
                        new CompareResult(
                                Lists.newArrayList(
                                        "路径:" + path + "下的数组类型对象属性存在不同:" + o1.toString()),
                                null));
            }
        }
        return Lists.newArrayList();
    }

    //    /**
    //     * 转为数组
    //     * @param array
    //     * @return
    //     */
    //    private Object[] convert2ObjectArray(Object array) {
    //        Class ofArray = array.getClass().getComponentType();
    //        if (ofArray.isPrimitive()) {
    //            List ar = new ArrayList();
    //            int length = Array.getLength(array);
    //            for (int i = 0; i < length; i++) {
    //                ar.add(Array.get(array, i));
    //            }
    //            return ar.toArray();
    //        } else {
    //            return (Object[]) array;
    //        }
    //    }
}

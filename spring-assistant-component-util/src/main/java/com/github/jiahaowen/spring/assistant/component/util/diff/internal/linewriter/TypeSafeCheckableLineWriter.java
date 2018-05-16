package com.github.jiahaowen.spring.assistant.component.util.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DiffException;
import java.util.List;

/**
 * 安全类型序列化抽象类
 *
 * @author jiahaowen.jhw
 * @version $Id: TypeSafeCheckableLineWriter.java, v 0.1 2016-10-30 下午9:40 jiahaowen.jhw Exp $
 */
public abstract class TypeSafeCheckableLineWriter<T> implements CheckableLineWriter {

    /**
     * @param path 路径描述了给定的对象在对象图中的位置.
     * @param value 待序列化的对象
     */
    abstract List<String> typeSafeWrite(String path, T value) throws DiffException;

    abstract List<CompareResult> typeSafeWrite(String path, T base, T working) throws DiffException;

    @Override
    public List<String> write(final String path, final Object value) throws DiffException {
        return typeSafeWrite(path, (T) value);
    }

    @Override
    public List<CompareResult> write(final String path, final Object base, final Object working)
            throws DiffException {
        return typeSafeWrite(path, (T) base, (T) working);
    }
}

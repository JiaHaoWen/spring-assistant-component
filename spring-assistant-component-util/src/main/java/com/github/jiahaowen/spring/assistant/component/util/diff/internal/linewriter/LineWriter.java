package com.github.jiahaowen.spring.assistant.component.util.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DiffException;
import java.util.List;

/**
 * @author jiahaowen.jhw
 * @version $Id: LineWriter.java, v 0.1 2016-10-30 下午9:38 jiahaowen.jhw Exp $
 */
public interface LineWriter {

    /**
     * 根据对象图中指定的路径,将给定的对象序列化为List<String>
     *
     * @param path 路径描述了给定的对象在对象图中的位置.
     * @param value 待序列化的对象
     */
    List<String> write(String path, Object value) throws DiffException;

    /** 对象比对 */
    List<CompareResult> write(String path, Object base, Object working) throws DiffException;
}

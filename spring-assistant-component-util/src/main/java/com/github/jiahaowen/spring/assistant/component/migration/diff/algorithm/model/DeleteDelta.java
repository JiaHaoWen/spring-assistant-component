package com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.model;

import com.github.jiahaowen.spring.assistant.component.migration.diff.exception.PatchFailedException;
import java.util.List;

/**
 * 描述比较两个文本之间的删除类型的差异量
 *
 * @author jiahaowen.jhw
 * @version $Id: DeleteDelta.java, v 0.1 2016-10-30 下午10:02 jiahaowen.jhw Exp $
 */
public class DeleteDelta extends Delta {

    public DeleteDelta(Chunk original, Chunk revised) {
        super(original, revised);
    }

    @Override
    public TYPE getType() {
        return Delta.TYPE.DELETE;
    }

    @Override
    public void verify(List<String> target) throws PatchFailedException {
        getOriginal().verify(target);
    }

    @Override
    public String toString() {
        return "[DeleteDelta, position: "
                + getOriginal().getPosition()
                + ", lines: "
                + getOriginal().getLines()
                + "]";
    }
}

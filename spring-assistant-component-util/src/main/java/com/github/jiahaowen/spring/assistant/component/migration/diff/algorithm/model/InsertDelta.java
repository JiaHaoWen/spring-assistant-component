package com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.model;

import com.github.jiahaowen.spring.assistant.component.migration.diff.exception.PatchFailedException;
import java.util.List;

/**
 * @author jiahaowen.jhw
 * @version $Id: InsertDelta.java, v 0.1 2016-10-30 下午10:03 jiahaowen.jhw Exp $
 */
public class InsertDelta extends Delta {

    public InsertDelta(Chunk original, Chunk revised) {
        super(original, revised);
    }

    @Override
    public void verify(List<String> target) throws PatchFailedException {
        if (getOriginal().getPosition() > target.size()) {
            throw new PatchFailedException(
                    "Incorrect patch for delta: " + "delta original position > target size");
        }
    }

    public TYPE getType() {
        return Delta.TYPE.INSERT;
    }

    @Override
    public String toString() {
        return "[InsertDelta, position: "
                + getOriginal().getPosition()
                + ", lines: "
                + getRevised().getLines()
                + "]";
    }
}

package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model;

import com.github.jiahaowen.spring.assistant.component.util.diff.exception.PatchFailedException;
import java.util.List;

/**
 * 描述比较文本之间的变化量
 *
 * @author jiahaowen.jhw
 * @version $Id: ChangeDelta.java, v 0.1 2016-10-30 下午9:57 jiahaowen.jhw Exp $
 */
public class ChangeDelta extends Delta {

    public ChangeDelta(Chunk original, Chunk revised) {
        super(original, revised);
    }

    @Override
    public void verify(List<String> target) throws PatchFailedException {
        getOriginal().verify(target);
        if (getOriginal().getPosition() > target.size()) {
            throw new PatchFailedException(
                    "Incorrect patch for delta: " + "delta original position > target size");
        }
    }

    @Override
    public String toString() {
        return "[ChangeDelta, position: "
                + getOriginal().getPosition()
                + ", lines: "
                + getOriginal().getLines()
                + " to "
                + getRevised().getLines()
                + "]";
    }

    @Override
    public TYPE getType() {
        return Delta.TYPE.CHANGE;
    }
}

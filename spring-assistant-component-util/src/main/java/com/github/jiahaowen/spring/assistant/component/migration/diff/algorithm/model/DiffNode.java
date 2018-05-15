package com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.model;

/**
 * DiffNode与其前向节点,标志所输入的两个String序列之间的变化量Delta. 也就是两个序列之间的差异子序列(长度可能为0)
 *
 * @author jiahaowen.jhw
 * @version $Id: DiffNode.java, v 0.1 2016-10-30 下午10:01 jiahaowen.jhw Exp $
 */
public final class DiffNode extends PathNode {

    public DiffNode(int i, int j, PathNode prev) {
        super(i, j, (prev == null ? null : prev.previousSnake()));
    }

    @Override
    public boolean isSnake() {
        return false;
    }
}

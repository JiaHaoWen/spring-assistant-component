package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model;

/**
 * 差异路径中的节点
 * @author jiahaowen.jhw
 * @version $Id: PathNode.java, v 0.1 2016-10-30 下午9:59 jiahaowen.jhw Exp $
 */
public abstract class PathNode {

    /** 基准序列中的位置 */
    public final int      i;

    /** 比较序列中的位置 */
    public final int      j;

    /** 路径中的前向节点 */
    public final PathNode prev;

    public PathNode(int i, int j, PathNode prev) {
        this.i = i;
        this.j = j;
        this.prev = prev;
    }

    /**
     * 是否为Snake
     */
    public abstract boolean isSnake();

    /**
     * 起始节点
     */
    public boolean isBootstrap() {
        return i < 0 || j < 0;
    }

    /**
     * 前向查找,直到遇到起始节点,或者Snake结束
     */
    public final PathNode previousSnake() {
        if (isBootstrap())
            return null;
        if (!isSnake() && prev != null)
            return prev.previousSnake();
        return this;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("[");
        PathNode node = this;
        while (node != null) {
            buf.append("(");
            buf.append(Integer.toString(node.i));
            buf.append(",");
            buf.append(Integer.toString(node.j));
            buf.append(")");
            node = node.prev;
        }
        buf.append("]");
        return buf.toString();
    }
}

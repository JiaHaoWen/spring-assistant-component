package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model;

/**
 * 描述差异路径中的信息
 *
 * Snake: 由连续匹配点形成的对角线，称为Snake
 *
 * @author jiahaowen.jhw
 * @version $Id: Snake.java, v 0.1 2016-10-30 下午10:00 jiahaowen.jhw Exp $
 */
public final class Snake extends PathNode {
    /**
     * 构造函数
     *
     * @param i 原始字符串序列中的位置
     * @param j 待比较字符串序列中的位置
     * @param prev 比较差异路径中的前向节点
     */
    public Snake(int i, int j, PathNode prev) {
        super(i, j, prev);
    }

    @Override
    public boolean isSnake() {
        return true;
    }

}

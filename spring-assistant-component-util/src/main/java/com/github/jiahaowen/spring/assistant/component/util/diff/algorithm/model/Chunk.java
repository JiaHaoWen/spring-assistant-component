package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model;

import com.github.jiahaowen.spring.assistant.component.util.diff.exception.PatchFailedException;
import java.util.List;

/**
 * 描述字符串比较过程中所涉及部分的信息
 *
 * @author jiahaowen.jhw
 * @version $Id: Chunk.java, v 0.1 2016-10-30 下午9:56 jiahaowen.jhw Exp $
 */
public class Chunk {

    /** 开始位置 */
    private final int position;

    /** 字符串序列 */
    private List<String> lines;

    /**
     * 构造函数.保存比较过程中涉及的字符串序列及其开始位置.
     *
     * @param position 开始位置
     * @param lines 涉及的字符串序列
     */
    public Chunk(int position, List<String> lines) {
        this.position = position;
        this.lines = lines;
    }

    /** 校验该Chubk中所保存的的String序列,是否符合给定序列中的文本信息 */
    public void verify(List<String> target) throws PatchFailedException {
        if (last() > target.size()) {
            throw new PatchFailedException("Incorrect Chunk: the position of chunk > target size");
        }
        for (int i = 0; i < size(); i++) {
            if (!target.get(position + i).equals(lines.get(i))) {
                throw new PatchFailedException(
                        "Incorrect Chunk: the chunk content doesn't match the target");
            }
        }
    }

    public int getPosition() {
        return position;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public int size() {
        return lines.size();
    }

    public int last() {
        return getPosition() + size() - 1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lines == null) ? 0 : lines.hashCode());
        result = prime * result + position;
        result = prime * result + size();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Chunk other = (Chunk) obj;
        if (lines == null) {
            if (other.lines != null) return false;
        } else if (!lines.equals(other.lines)) return false;
        if (position != other.position) return false;
        return true;
    }

    @Override
    public String toString() {
        return "[position: " + position + ", size: " + size() + ", lines: " + lines + "]";
    }
}

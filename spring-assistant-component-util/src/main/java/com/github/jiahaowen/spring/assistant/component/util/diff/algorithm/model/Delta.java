package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model;

import com.github.jiahaowen.spring.assistant.component.util.diff.exception.PatchFailedException;
import java.util.List;

/**
 * 描述待比较String序列之间的变化量
 *
 * @author jiahaowen.jhw
 * @version $Id: Delta.java, v 0.1 2016-10-30 下午9:56 jiahaowen.jhw Exp $
 */
public abstract class Delta {

    /** 初始比较量 */
    private Chunk original;

    /** 变化量 */
    private Chunk revised;

    /** 构造函数 */
    public Delta(Chunk original, Chunk revised) {
        if (original == null) {
            throw new IllegalArgumentException("original must not be null");
        }
        if (revised == null) {
            throw new IllegalArgumentException("revised must not be null");
        }
        this.original = original;
        this.revised = revised;
    }

    /** 校验是否可应用到指定的字符串序列 */
    public abstract void verify(List<String> target) throws PatchFailedException;

    /** 返回变量类型 */
    public abstract TYPE getType();

    public Chunk getOriginal() {
        return original;
    }

    public void setOriginal(Chunk original) {
        this.original = original;
    }

    public Chunk getRevised() {
        return revised;
    }

    public void setRevised(Chunk revised) {
        this.revised = revised;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((original == null) ? 0 : original.hashCode());
        result = prime * result + ((revised == null) ? 0 : revised.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Delta other = (Delta) obj;
        if (original == null) {
            if (other.original != null) return false;
        } else if (!original.equals(other.original)) return false;
        if (revised == null) {
            if (other.revised != null) return false;
        } else if (!revised.equals(other.revised)) return false;
        return true;
    }

    /** 以初始字符串序列为基准,定义变化量的类型 */
    public enum TYPE {
        /** 变化 */
        CHANGE,
        /** 删除 */
        DELETE,
        /** 新增 */
        INSERT
    }
}

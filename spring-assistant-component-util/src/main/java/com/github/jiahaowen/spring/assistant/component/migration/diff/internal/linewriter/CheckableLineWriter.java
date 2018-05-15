package com.github.jiahaowen.spring.assistant.component.migration.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.migration.diff.differ.Checkable;

/**
 * 自带校验功能的序列化接口
 *
 * @author jiahaowen.jhw
 * @version $Id: CheckableLineWriter.java, v 0.1 2016-10-30 下午9:37 jiahaowen.jhw Exp $
 */
public interface CheckableLineWriter extends LineWriter, Checkable {}

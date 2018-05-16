package com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer;

import com.github.jiahaowen.spring.assistant.component.util.diff.differ.Checkable;

/**
 * 该接口可序列化特定的对象
 *
 * @author jiahaowen.jhw
 * @version $Id: CheckableSerializer.java, v 0.1 2016-10-30 下午9:30 jiahaowen.jhw Exp $
 */
public interface CheckableSerializer<T> extends Serializer<T>, Checkable {}

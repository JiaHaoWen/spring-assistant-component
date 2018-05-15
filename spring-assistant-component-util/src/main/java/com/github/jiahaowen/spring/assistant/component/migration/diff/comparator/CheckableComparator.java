package com.github.jiahaowen.spring.assistant.component.migration.diff.comparator;

import com.github.jiahaowen.spring.assistant.component.migration.diff.differ.Checkable;
import java.util.Comparator;

/**
 * @author jiahaowen.jhw
 * @version $Id: CheckableComparator.java, v 0.1 2016-10-30 下午9:27 jiahaowen.jhw Exp $
 */
public interface CheckableComparator<T> extends Comparator<T>, Checkable {}

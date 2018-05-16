package com.github.jiahaowen.spring.assistant.component.util.diff.algorithm;

import com.github.jiahaowen.spring.assistant.component.util.diff.algorithm.model.Delta;
import java.io.Serializable;
import java.util.Comparator;

/**
 * 变量信息比较器
 *
 * @author jiahaowen.jhw
 * @version $Id: DeltaComparator.java, v 0.1 2016-10-30 下午9:57 jiahaowen.jhw Exp $
 */
public class DeltaComparator implements Comparator<Delta>, Serializable {

    public static final Comparator<Delta> INSTANCE = new DeltaComparator();

    private static final long serialVersionUID = 1L;

    private DeltaComparator() {}

    @Override
    public int compare(final Delta a, final Delta b) {
        final int posA = a.getOriginal().getPosition();
        final int posB = b.getOriginal().getPosition();
        if (posA > posB) {
            return 1;
        } else if (posA < posB) {
            return -1;
        }
        return 0;
    }
}

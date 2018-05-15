/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.model;


import com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.DeltaComparator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述待比较String序列间所有的不同点
 * @author jiahaowen.jhw
 * @version $Id: Patch.java, v 0.1 2016-10-30 下午9:54 jiahaowen.jhw Exp $
 */
public class Patch {

    private List<Delta> deltas = new LinkedList<Delta>();

    public void addDelta(Delta delta) {
        deltas.add(delta);
    }

    public List<Delta> getDeltas() {
        Collections.sort(deltas, DeltaComparator.INSTANCE);
        return deltas;
    }
}

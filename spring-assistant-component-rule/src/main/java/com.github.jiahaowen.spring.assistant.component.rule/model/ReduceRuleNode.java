package com.github.jiahaowen.spring.assistant.component.rule.model;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jiahaowen.jhw
 * @version $Id: ReduceRuleNode.java, v 0.1 2016-12-07 下午6:58 jiahaowen.jhw Exp $
 */
public class ReduceRuleNode<Resp extends RuleResponse> implements RuleNode<Resp> {

    private List<RuleNode<Resp>> ruleNodes = new ArrayList<RuleNode<Resp>>();
    private List<RuleNode<Resp>> targetNodes = new ArrayList<RuleNode<Resp>>();
    private ReduceFunction<Resp> reduceFunction;

    public ReduceRuleNode(ReduceFunction<Resp> reduceFunction, RuleNode<Resp>... ruleNodes) {
        Collections.addAll(this.ruleNodes, ruleNodes);
        this.reduceFunction = reduceFunction;
    }

    @Override
    public Object execute() {
        if (targetNodes.size() == 0) {
            // FIXME should throw exception.
            return null;
        }
        List<Object> respList = new ArrayList<Object>();
        for (RuleNode<Resp> ruleNode : targetNodes) {
            final Object resp = ruleNode.execute();
            if (resp != null) {
                respList.add(resp);
            }
        }

        return reduceFunction.invoke(respList);
    }

    @Override
    public boolean condition() {
        // TODO 是否支持可重入？ 需要考虑下
        for (RuleNode<Resp> ruleNode : ruleNodes) {
            if (ruleNode.condition()) {
                targetNodes.add(ruleNode);
            }
        }
        return targetNodes.size() > 0;
    }

    @Override
    public NodeStructureSpec visit() {
        NodeStructureSpec spec = new NodeStructureSpec();
        spec.nodeType = "reduce";
        List<NodeStructureSpec> nodeStructureSpecs = Lists.newArrayList();
        for (RuleNode<Resp> ruleNode : ruleNodes) {
            nodeStructureSpecs.add(ruleNode.visit());
        }
        spec.subNodeSpecs = nodeStructureSpecs;
        spec.function = reduceFunction.code();
        return spec;
    }

    @Override
    public String description() {
        return null;
    }
}

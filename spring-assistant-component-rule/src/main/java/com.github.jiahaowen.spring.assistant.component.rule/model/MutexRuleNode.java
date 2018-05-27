package com.github.jiahaowen.spring.assistant.component.rule.model;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jiahaowen.jhw
 * @version $Id: MutexRuleNode.java, v 0.1 2016-12-07 下午6:56 jiahaowen.jhw Exp $
 */
public class MutexRuleNode<Resp extends RuleResponse> implements RuleNode<Resp> {
    private List<RuleNode<Resp>> ruleNodes = new ArrayList<RuleNode<Resp>>();

    private RuleNode<Resp> selectedRuleNode;

    public MutexRuleNode(RuleNode<Resp>... ruleNodes) {
        //        if (ruleNodes.size() == 0) {
        //            throw new IllegalArgumentException("ruleNodes' size should be positive.");
        //        }
        //        this.ruleNodes = ruleNodes;
        Collections.addAll(this.ruleNodes, ruleNodes);
    }

    @Override
    public Object execute() {
        if (selectedRuleNode == null) {
            throw new RuntimeException("wrong entrance.");
        }
        return selectedRuleNode.execute();
    }

    @Override
    public boolean condition() {
        RuleNode<Resp> node = null;
        for (RuleNode<Resp> ruleNode : ruleNodes) {
            if (ruleNode.condition()) {
                // TODO trace the ruleNode
                node = ruleNode;
                break;
            }
        }
        selectedRuleNode = node;
        return selectedRuleNode != null;
    }

    @Override
    public NodeStructureSpec visit() {
        NodeStructureSpec spec = new NodeStructureSpec();
        spec.nodeType = "mutex";
        List<NodeStructureSpec> nodeStructureSpecs = Lists.newArrayList();
        for (RuleNode<Resp> ruleNode : ruleNodes) {
            nodeStructureSpecs.add(ruleNode.visit());
        }
        spec.subNodeSpecs = nodeStructureSpecs;
        return spec;
    }

    @Override
    public String description() {
        return null;
    }
}

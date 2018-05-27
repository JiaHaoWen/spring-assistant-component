package com.github.jiahaowen.spring.assistant.component.rule.model;

/**
 * @author jiahaowen.jhw
 * @version $Id: GeneralRuleNode.java, v 0.1 2016-12-07 下午6:57 jiahaowen.jhw Exp $
 */
public class GeneralRuleNode<Resp extends RuleResponse> implements
    RuleNode<Resp> {
    private Rule<Resp> targetRule;

    public GeneralRuleNode(Rule<Resp> targetRule) {
        this.targetRule = targetRule;
    }

    @Override
    public Object execute() {
        // return targetRule.getCondition().call() ? targetRule.getExecuteBody().call() : null;
        return targetRule.getResponseGenerator().generate();
    }

    @Override
    public boolean condition() {
        // return targetRule.getCondition().call();
        return targetRule.getRuleCondition().condition();
    }

    @Override
    public NodeStructureSpec visit() {
        NodeStructureSpec spec = new NodeStructureSpec();
        spec.nodeType = "general";
        spec.ruleSpec = targetRule.buildRuleSpec();
        return spec;
    }

    @Override
    public String description() {
        return targetRule.getDescription();
    }
}

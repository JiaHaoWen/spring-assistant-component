package com.github.jiahaowen.spring.assistant.component.rule.model;

import com.github.jiahaowen.spring.assistant.component.rule.util.GroovyClosureUtil;
import groovy.lang.Closure;
import java.io.Serializable;
import java.util.List;

/**
 * @author jiahaowen.jhw
 * @version $Id: Rule.java, v 0.1 2016-12-07 下午3:04 jiahaowen.jhw Exp $
 */
public class Rule<Resp extends RuleResponse> implements Serializable {

    private static final long serialVersionUID = 7113219885851941451L;

    private String description;

    private RuleCondition ruleCondition;

    private RuleResponseGenerator<Resp> responseGenerator;

    public Rule(
            String description,
            RuleCondition ruleCondition,
            RuleResponseGenerator<Resp> responseGenerator) {
        this.description = description;
        this.ruleCondition = ruleCondition;
        this.responseGenerator = responseGenerator;
    }

    public static <Resp extends RuleResponse> RuleNode<Resp> makeRule(
            final String description,
            final Closure<Boolean> condition,
            final Closure<Resp> executeBody) {

        Rule<Resp> rule =
                new Rule<Resp>(
                        description,
                        new RuleCondition() {
                            @Override
                            public boolean condition() {
                                return condition.call();
                            }

                            @Override
                            public String description() {
                                return "条件";
                            }

                            @Override
                            public String code() {
                                return GroovyClosureUtil.getClosureStatementCode(condition);
                            }
                        },
                        new RuleResponseGenerator<Resp>() {
                            @Override
                            public Object generate() {
                                return executeBody.call();
                            }

                            @Override
                            public String description() {
                                return "结果";
                            }

                            @Override
                            public String code() {
                                return GroovyClosureUtil.getClosureStatementCode(executeBody);
                            }
                        });
        return buildSimpleNode(rule);
    }

    public static <Resp extends RuleResponse> RuleNode<Resp> makeRule(
            final String description,
            final String conditionDescription,
            final Closure<Boolean> condition,
            final String responseDescription,
            final Closure<Resp> executeBody) {

        Rule<Resp> rule =
                new Rule<Resp>(
                        description,
                        new RuleCondition() {
                            @Override
                            public boolean condition() {
                                return condition.call();
                            }

                            @Override
                            public String description() {
                                return conditionDescription;
                            }

                            @Override
                            public String code() {
                                return GroovyClosureUtil.getClosureStatementCode(condition);
                            }
                        },
                        new RuleResponseGenerator<Resp>() {
                            @Override
                            public Object generate() {
                                return executeBody.call();
                            }

                            @Override
                            public String description() {
                                return responseDescription;
                            }

                            @Override
                            public String code() {
                                return GroovyClosureUtil.getClosureStatementCode(executeBody);
                            }
                        });
        return buildSimpleNode(rule);
    }

    public static <Resp extends RuleResponse> Rule<Resp> createQLRule(final String description) {
        return null;
    }

    public static <Resp extends RuleResponse> RuleNode<Resp> buildSimpleNode(Rule<Resp> rule) {
        return new GeneralRuleNode<Resp>(rule);
    }

    public static <Resp extends RuleResponse> RuleNode<Resp> mutex(RuleNode<Resp>... ruleNodes) {
        return new MutexRuleNode<Resp>(ruleNodes);
    }

    private static <Resp extends RuleResponse> RuleNode<Resp> buildReduceNode(
            ReduceFunction<Resp> reduceFunction, RuleNode<Resp>... ruleNodes) {
        return new ReduceRuleNode<Resp>(reduceFunction, ruleNodes);
    }

    public static <Resp extends RuleResponse> RuleNode<Resp> reduce(
            final Closure<Resp> reduceClosure, RuleNode<Resp>... ruleNodes) {
        return new ReduceRuleNode<Resp>(
                new ReduceFunction<Resp>() {
                    @Override
                    public Object invoke(List<Object> respList) {
                        return reduceClosure.call(respList);
                    }

                    @Override
                    public String code() {
                        return GroovyClosureUtil.getClosureStatementCode(reduceClosure);
                    }
                },
                ruleNodes);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RuleCondition getRuleCondition() {
        return ruleCondition;
    }

    public RuleResponseGenerator<Resp> getResponseGenerator() {
        return responseGenerator;
    }

    public NodeStructureSpec.RuleSpec buildRuleSpec() {
        NodeStructureSpec.RuleSpec ruleSpec = new NodeStructureSpec.RuleSpec();
        ruleSpec.description = description;
        ruleSpec.condition = this.ruleCondition.description();
        ruleSpec.conditionCode = this.ruleCondition.code();
        ruleSpec.response = this.responseGenerator.description();
        ruleSpec.responseCode = this.responseGenerator.code();
        return ruleSpec;
    }
}

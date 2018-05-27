package com.github.jiahaowen.spring.assistant.component.rule.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author jiahaowen.jhw
 * @version $Id: NodeStructureSpec.java, v 0.1 2016-12-01 下午9:05 jiahaowen.jhw Exp $
 */
public class NodeStructureSpec implements Serializable {

    private static final long serialVersionUID = 6533984429319695028L;

    public String nodeType;

    public RuleSpec ruleSpec;

    public String function;

    public List<NodeStructureSpec> subNodeSpecs;

    public static class RuleSpec implements Serializable {

        private static final long serialVersionUID = 951495229321631626L;

        public String description;

        public String condition;

        public String conditionCode;

        public String response;

        public String responseCode;
    }
}

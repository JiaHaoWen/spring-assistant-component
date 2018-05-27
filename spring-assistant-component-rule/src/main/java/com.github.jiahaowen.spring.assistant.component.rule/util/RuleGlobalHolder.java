package com.github.jiahaowen.spring.assistant.component.rule.util;

import com.github.jiahaowen.spring.assistant.component.rule.api.RuleRunner;
import com.github.jiahaowen.spring.assistant.component.util.common.util.StringUtil;
import java.util.HashSet;
import java.util.Set;

/**
 * 全局持有器，持有一些全局的变量
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleGlobalHolder.java, v 0.1 2016-12-01 下午9:02 jiahaowen.jhw Exp $
 */
public class RuleGlobalHolder {

    public static Set<String> nameSpaces = new HashSet<String>();

    public static RuleRunner ruleRunner;

    public static void addNameSpace(String nameSpace[]) {
        if (nameSpace != null) {
            for (String ns : nameSpace) {
                addNameSpace(ns);
            }
        }
    }

    public static void addNameSpace(String nameSpace) {
        if (nameSpaces == null) {
            nameSpaces = new HashSet<String>();
        }
        if (StringUtil.isBlank(nameSpace)) {
            return;
        }
        if (nameSpaces.contains(nameSpace) || nameSpaces.size() > 100) {
            return;
        }
        nameSpaces.add(nameSpace.trim());
    }

    public static Set<String> getNameSpaces() {
        return nameSpaces;
    }

    public static void setRuleRunner(RuleRunner rn) {
        ruleRunner = rn;
    }
}

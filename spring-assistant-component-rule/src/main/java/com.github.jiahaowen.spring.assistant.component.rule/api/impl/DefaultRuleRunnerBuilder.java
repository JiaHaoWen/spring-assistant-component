package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.api.ContextExtension;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleContextLoader;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleEngine;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleRunner;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleRunnerBuilder;
import com.github.jiahaowen.spring.assistant.component.rule.api.RuleScriptLoader;
import com.github.jiahaowen.spring.assistant.component.rule.model.ScriptLanguage;
import com.github.jiahaowen.spring.assistant.component.rule.repository.repository.ScriptDataService;
import com.github.jiahaowen.spring.assistant.component.rule.util.RuleGlobalHolder;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 默认规则引擎构造器
 *
 * @author jiahaowen.jhw
 * @version $Id: DefaultRuleRunnerBuilder.java, v 0.1 2016-12-01 下午8:17 jiahaowen.jhw Exp $
 */
@Component
public class DefaultRuleRunnerBuilder implements RuleRunnerBuilder {

    /** 默认日志类 */
    private static final Logger logger = Logger.getLogger(DefaultRuleRunnerBuilder.class);

    /** 脚本数据服务 */
    @Autowired private ScriptDataService scriptDataService;

    //    /**需要预先装载到本地缓存的业务规则*/
    //    private String[]             preloadRules;

    /** 当前环境所处的业务空间 */
    private String[] nameSpaces;

    /** groovy规则脚本执行引擎 */
    @Autowired private GroovyRuleEngineImpl defaultGroovyEngine;

    @Autowired private SimpleGroovyRuleEngineImpl simpleGroovyRuleEngine;

    /** groovy脚本编译器 */
    @Autowired private GroovyCompiler groovyCompiler;

    /**
     * 构建一个规则执行器
     *
     * @return
     */
    @Override
    public RuleRunner buildRuleRunner() {

        DefaultRuleRunner ruleRunner = new DefaultRuleRunner();
        RuleScriptLoader ruleScriptLoader = initRuleScriptLoader();
        RuleContextLoader ruleContextLoader = initRuleContextLoader();
        ruleRunner.setRuleContextLoader(ruleContextLoader);
        ruleRunner.setRuleScriptLoader(ruleScriptLoader);
        ruleRunner.setRuleEngines(initRuleEngines());

        // TODO:是否考虑接入缓存??

        // RuleGlobalHolder.addNameSpace(nameSpaces);
        RuleGlobalHolder.setRuleRunner(ruleRunner);
        return ruleRunner;
    }

    /**
     * 初始化规则脚本加载器
     *
     * @return
     */
    private RuleScriptLoader initRuleScriptLoader() {
        DefaultRuleScriptLoader ruleScriptLoader = new DefaultRuleScriptLoader();
        ruleScriptLoader.setScriptDataService(scriptDataService);

        return ruleScriptLoader;
    }

    /**
     * 初始化规则上下文
     *
     * @return
     */
    private RuleContextLoader initRuleContextLoader() {
        DefaultRuleContextLoader ruleContextLoader = new DefaultRuleContextLoader();
        ruleContextLoader.setLanguageContextExtensions(initLanguageContextExtensions());
        return ruleContextLoader;
    }

    /**
     * 规则引擎初始化
     *
     * @return
     */
    private Map<String, RuleEngine> initRuleEngines() {
        Map<String, RuleEngine> ruleEngines = Maps.newHashMap();
        ruleEngines.put(ScriptLanguage.groovy.name(), defaultGroovyEngine);
        ruleEngines.put(ScriptLanguage.simpleGroovy.name(), simpleGroovyRuleEngine);
        return ruleEngines;
    }

    /**
     * 规则脚本语言级别扩展点
     *
     * @return
     */
    private Map<String, List<ContextExtension>> initLanguageContextExtensions() {
        GroovyFuncContextExtension groovyFuncCxtContribution = initGroovyFuncCxtExtension();
        Map<String, List<ContextExtension>> languageContextContributions = Maps.newHashMap();
        List<ContextExtension> contributionList = new ArrayList<ContextExtension>();
        contributionList.add(groovyFuncCxtContribution);
        languageContextContributions.put(ScriptLanguage.groovy.name(), contributionList);
        languageContextContributions.put(ScriptLanguage.simpleGroovy.name(), contributionList);
        return languageContextContributions;
    }

    /**
     * groovy脚本扩展点
     *
     * @return
     */
    private GroovyFuncContextExtension initGroovyFuncCxtExtension() {
        GroovyFuncContextExtension groovyFuncCxtExtension = new GroovyFuncContextExtension();
        return groovyFuncCxtExtension;
    }

    /**
     * Setter method for property nameSpaces.
     *
     * @param nameSpaces value to be assigned to property nameSpaces
     */
    public void setNameSpaces(String[] nameSpaces) {
        this.nameSpaces = nameSpaces;
    }
}

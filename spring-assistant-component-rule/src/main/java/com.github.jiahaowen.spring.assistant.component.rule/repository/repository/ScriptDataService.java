package com.github.jiahaowen.spring.assistant.component.rule.repository.repository;

import com.github.jiahaowen.spring.assistant.component.rule.database.RuleScriptSimpleDO;
import com.github.jiahaowen.spring.assistant.component.rule.model.ResultDO;
import java.util.List;

/**
 * 规则数据服务
 *
 * @author jiahaowen.jhw
 * @version $Id: ScriptDataService.java, v 0.1 2016-12-01 下午5:15 jiahaowen.jhw Exp $
 */
public interface ScriptDataService {

    // TODO:考虑接入缓存

    /**
     * 根据规则脚本名字 获得一个规则脚本对象
     *
     * @param name
     * @param fromCache
     * @return
     */
    public RuleScriptSimpleDO queryRuleScripByName(String name, boolean fromCache);

    /**
     * 根据规则脚本名字 获得一个规则脚本对象
     *
     * @param names
     * @param fromCache
     * @return
     */
    public List<RuleScriptSimpleDO> queryRuleScripByNames(List<String> names, boolean fromCache);

    /**
     * 创建一个规则脚本对象
     *
     * @param ruleScriptDO
     * @return
     */
    public long createRuleScript(RuleScriptSimpleDO ruleScriptDO);

    /**
     * 更新一个规则脚本对象
     *
     * @param ruleScriptDO
     * @return
     */
    public int updateRuleScript(RuleScriptSimpleDO ruleScriptDO);

    /**
     * 根据脚本语言类型和业务环节查询对应的规则脚本
     *
     * @param language
     * @param business
     * @return
     */
    public List<RuleScriptSimpleDO> queryRuleScriptByLanguageAndBusiness(
            String language, String[] business);

    /**
     * 批量查询获得一批规则脚本对象
     *
     * @param language
     * @return
     */
    public List<RuleScriptSimpleDO> queryFunctionScriptsByLanguage(String language);

    /**
     * 批量查询获得一批规则脚本对象
     *
     * @param language
     * @return
     */
    public List<RuleScriptSimpleDO> queryFunctionScriptsByLanguage(
            String language, String nameSpace);

    /**
     * 通过id查询规则脚本，不走DB
     *
     * @param id
     * @return
     */
    public RuleScriptSimpleDO queryRuleScripByID(Long id);

    /**
     * 根据条件分页查询规则脚本
     *
     * @param queryParam
     * @return
     */
    // TODO:规则分页查询

    /**
     * 根据id删除规则脚本
     *
     * @param id
     * @param moduleName
     * @return
     */
    public int deleteRuleScriptByID(Long id, String moduleName);

    /**
     * 根据空间名和规则名推送脚本
     *
     * @param
     * @param moduleName
     * @return
     */
    public ResultDO pushRuleScript(String moduleName);
}

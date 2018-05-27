package com.github.jiahaowen.spring.assistant.component.rule.repository.repository;

import com.github.jiahaowen.spring.assistant.component.rule.database.RuleScriptSimpleDAO;
import com.github.jiahaowen.spring.assistant.component.rule.database.RuleScriptSimpleDO;
import com.github.jiahaowen.spring.assistant.component.rule.model.ResultDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数据服务实现
 *
 * @author jiahaowen.jhw
 * @version $Id: ScriptDataServiceImpl.java, v 0.1 2016-12-13 下午4:33 jiahaowen.jhw Exp $
 */
@Component
public class ScriptDataServiceImpl implements ScriptDataService {

    // TODO:
    @Autowired private RuleScriptSimpleDAO ruleScriptSimpleDAO;

    /**
     * 根据规则脚本名字 获得一个规则脚本对象
     *
     * @param name
     * @param fromCache
     * @return
     */
    @Override
    public RuleScriptSimpleDO queryRuleScripByName(String name, boolean fromCache) {
        // TODO:
        return ruleScriptSimpleDAO.queryByName(name);
    }

    /**
     * 根据规则脚本名字 获得一个规则脚本对象
     *
     * @param names
     * @param fromCache
     * @return
     */
    @Override
    public List<RuleScriptSimpleDO> queryRuleScripByNames(List<String> names, boolean fromCache) {
        // TODO:
        return ruleScriptSimpleDAO.queryByNames(names);
    }

    /**
     * 创建一个规则脚本对象
     *
     * @param ruleScriptDO
     * @return
     */
    @Override
    public long createRuleScript(RuleScriptSimpleDO ruleScriptDO) {
        // TODO:
        return ruleScriptSimpleDAO.insert(ruleScriptDO);
    }

    /**
     * 更新一个规则脚本对象
     *
     * @param ruleScriptDO
     * @return
     */
    @Override
    public int updateRuleScript(RuleScriptSimpleDO ruleScriptDO) {
        // TODO:
        return 0;
    }

    /**
     * 根据脚本语言类型和业务环节查询对应的规则脚本
     *
     * @param language
     * @param business
     * @return
     */
    @Override
    public List<RuleScriptSimpleDO> queryRuleScriptByLanguageAndBusiness(
            String language, String[] business) {
        // TODO:
        return null;
    }

    /**
     * 批量查询获得一批规则脚本对象
     *
     * @param language
     * @return
     */
    @Override
    public List<RuleScriptSimpleDO> queryFunctionScriptsByLanguage(String language) {
        // TODO:
        return null;
    }

    /**
     * 批量查询获得一批规则脚本对象
     *
     * @param language
     * @param nameSpace
     * @return
     */
    @Override
    public List<RuleScriptSimpleDO> queryFunctionScriptsByLanguage(
            String language, String nameSpace) {
        // TODO:
        return null;
    }

    /**
     * 通过id查询规则脚本，不走DB
     *
     * @param id
     * @return
     */
    @Override
    public RuleScriptSimpleDO queryRuleScripByID(Long id) {
        // TODO:
        return ruleScriptSimpleDAO.queryById(id);
    }

    /**
     * 根据id删除规则脚本
     *
     * @param id
     * @param moduleName
     * @return
     */
    @Override
    public int deleteRuleScriptByID(Long id, String moduleName) {
        // TODO:
        return 0;
    }

    /**
     * 根据空间名和规则名推送脚本
     *
     * @param moduleName
     * @return
     */
    @Override
    public ResultDO pushRuleScript(String moduleName) {
        // TODO:
        return null;
    }
}

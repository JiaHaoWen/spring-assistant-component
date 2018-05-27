package com.github.jiahaowen.spring.assistant.component.rule.database;

import java.util.List;

/**
 * 需要被底层物理DB实现
 */
public interface RuleScriptSimpleDAO {

    /**
     * Insert one <tt>RuleScriptSimpleDO</tt> object to DB table <tt>rule_script_simple</tt>, return
     * primary key
     *
     * <p>Description for this operation is<br>
     * <tt></tt>
     *
     * <p>The sql statement for this operation is <br>
     * <tt>insert into
     * rule_script_simple(id,name_space,rule_name,rule_desc,rule_body,language_type,status,gmt_create,gmt_modified,last_moder,moder_tp)
     * values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
     *
     * @param ruleScriptSimple ruleScriptSimple
     * @return long
     */
    public long insert(RuleScriptSimpleDO ruleScriptSimple);

    /**
     * Query DB table <tt>rule_script_simple</tt> for records.
     *
     * <p>Description for this operation is<br>
     * <tt></tt>
     *
     * <p>The sql statement for this operation is <br>
     * <tt>select id, name_space, rule_name, rule_desc, rule_body, language_type, status,
     * gmt_create, gmt_modified, last_moder, moder_tp from rule_script_simple where (id = ?)</tt>
     *
     * @param id id
     * @return RuleScriptSimpleDO
     */
    public RuleScriptSimpleDO queryById(long id);

    /**
     * Query DB table <tt>rule_script_simple</tt> for records.
     *
     * <p>Description for this operation is<br>
     * <tt></tt>
     *
     * <p>The sql statement for this operation is <br>
     * <tt>select id, name_space, rule_name, rule_desc, rule_body, language_type, status,
     * gmt_create, gmt_modified, last_moder, moder_tp from rule_script_simple where (rule_name =
     * ?)</tt>
     *
     * @param ruleName ruleName
     * @return RuleScriptSimpleDO
     */
    public RuleScriptSimpleDO queryByName(String ruleName);

    /**
     * Query DB table <tt>rule_script_simple</tt> for records.
     *
     * <p>Description for this operation is<br>
     * <tt></tt>
     *
     * <p>The sql statement for this operation is <br>
     * <tt>select id, name_space, rule_name, rule_desc, rule_body, language_type, status,
     * gmt_create, gmt_modified, last_moder, moder_tp from rule_script_simple</tt>
     *
     * @param names names
     * @return List<RuleScriptSimpleDO>
     */
    public List<RuleScriptSimpleDO> queryByNames(List<?> names);

    /**
     * Query DB table <tt>rule_script_simple</tt> for records.
     *
     * <p>Description for this operation is<br>
     * <tt></tt>
     *
     * <p>The sql statement for this operation is <br>
     * <tt>select id, name_space, rule_name, rule_desc, rule_body, language_type, status,
     * gmt_create, gmt_modified, last_moder, moder_tp from rule_script_simple where ((name_space =
     * ?) AND (language_type = ?))</tt>
     *
     * @param nameSpace nameSpace
     * @param languageType languageType
     * @return List<RuleScriptSimpleDO>
     */
    public List<RuleScriptSimpleDO> queryByLanguageTypeAndNameSpace(
            String nameSpace, String languageType);
}

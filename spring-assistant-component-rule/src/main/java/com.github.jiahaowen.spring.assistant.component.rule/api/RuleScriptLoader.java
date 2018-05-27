package com.github.jiahaowen.spring.assistant.component.rule.api;


import com.github.jiahaowen.spring.assistant.component.rule.model.RuleScript;
import java.util.List;

/**
 * 脚本装载器
 *
 * @author jiahaowen.jhw
 * @version $Id: RuleScriptLoader.java, v 0.1 2016-12-01 下午5:16 jiahaowen.jhw Exp $
 */
public interface RuleScriptLoader {

    /**
     * 预加载某一些业务类型的规则脚本
     *
     * <p>一般在应用启动是做，避免在运行时远程访问请求规则脚本
     *
     * @param businessNames
     */
    void preLoad(String[] businessNames);

    /**
     * 装载一个规则脚本
     *
     * @param name 规则名字
     * @return
     */
    RuleScript load(String name);

    /**
     * 装载多个规则脚本
     *
     * @param name 规则名字
     * @return
     */
    List<RuleScript> load(List<String> name);
}

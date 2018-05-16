package com.github.jiahaowen.spring.assistant.component.cache.test.script;

import com.github.jiahaowen.spring.assistant.component.cache.script.AbstractScriptParser;
import com.github.jiahaowen.spring.assistant.component.cache.script.JavaScriptParser;
import com.github.jiahaowen.spring.assistant.component.cache.script.OgnlParser;
import com.github.jiahaowen.spring.assistant.component.cache.script.SpringELParser;
import com.github.jiahaowen.spring.assistant.component.cache.test.Stopwatch;
import org.junit.Test;

/**
 * 性能测试
 * @author jiahaowen
 */
public class ScriptTest {

    private static int hot=10000;

    private static int run=100000;

    @Test
    public void testScript() throws Exception {
        String keySpEL="'test_'+#args[0]+'_'+#args[1]";
        Object[] arguments=new Object[]{"1111", "2222"};
        testScriptParser(new SpringELParser(), keySpEL, arguments);
        testScriptParser(new OgnlParser(), keySpEL, arguments);
        keySpEL="'test_'+args[0]+'_'+args[1]";
        testScriptParser(new JavaScriptParser(), keySpEL, arguments);
    }

    private void testScriptParser(AbstractScriptParser scriptParser, String script, Object[] args) throws Exception {
        for(int i=0; i < hot; i++) {
            scriptParser.getDefinedCacheKey(script, null, args, null, false);
        }

        Stopwatch sw= Stopwatch.begin();
        for(int i=0; i < run; i++) {
            scriptParser.getDefinedCacheKey(script, null, args, null, false);
        }
        sw.stop();
        System.out.println(scriptParser.getClass().getName() + "--->" + sw);
    }
}

package com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent;

import com.github.jiahaowen.spring.assistant.component.concurrent.internal.callback.BizServiceCallBack;
import com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent.models.Person;
import com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent.models.TestResult;
import com.github.jiahaowen.spring.assistant.component.util.common.error.ServiceException;
import com.github.jiahaowen.spring.assistant.component.util.common.result.Result;
import com.github.jiahaowen.spring.assistant.component.util.common.util.StringUtil;
import org.springframework.stereotype.Component;

/**
 * 业务逻辑模拟类，真实业务逻辑类似使用方法
 *
 * @author chuanmu
 * @since 2017/11/20
 */
@Component("testBizExecService")
public class TestBizExecService implements BizServiceCallBack<Person, TestResult> {

    @Override
    public Result<TestResult> doBiz(Person person) {

        final Result<TestResult> result = new Result<>();
        result.setSuccess(true);
        result.setResultObj(new TestResult(person.getName()));

        // 模拟某次请求错误
        if (StringUtil.equals(person.getName(), "singleRequestWrong")) {
            throw ServiceException.buildInternalEx("模拟某次请求错误");
        }

        return result;
    }
}

package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.jvm.service.impl;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.jvm.service.FetchMethodBService;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author jiahaowen
 * @version $Id: FetchJvmRefService.java, v 0.1 16/11/22 下午3:14 jiahaowen Exp $
 */
@Component
public class FetchJvmRefService implements FetchMethodBService {

    @Override
    public Method getBeanMethodBRef(Method method, String methodName) {
        if (StringUtils.isBlank(methodName)) {
            methodName = String.format("%sB", method.getName());
        }

        return ReflectionUtils.findMethod(
                method.getDeclaringClass(), methodName, method.getParameterTypes());
    }
}

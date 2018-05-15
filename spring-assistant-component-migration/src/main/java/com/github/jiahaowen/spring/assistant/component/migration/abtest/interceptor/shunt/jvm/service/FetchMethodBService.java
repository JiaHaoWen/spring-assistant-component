package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.shunt.jvm.service;

import java.lang.reflect.Method;

/**
 * 获取方法B的服务
 *
 * @author jiahaowen
 * @version $Id: FetchMethodBService.java, v 0.1 16/11/22 下午3:12 jiahaowen Exp $
 */
public interface FetchMethodBService {

    Method getBeanMethodBRef(Method method, String methodName);
}

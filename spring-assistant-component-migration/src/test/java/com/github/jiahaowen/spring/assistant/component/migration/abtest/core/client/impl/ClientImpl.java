package com.github.jiahaowen.spring.assistant.component.migration.abtest.core.client.impl;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.ABTest;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.core.client.Client;
import org.springframework.stereotype.Component;

/**
 * @author jiahaowen
 * @version $Id: ClientImpl.java, v 0.1 16/11/22 下午3:55 jiahaowen Exp $
 */
@Component
public class ClientImpl implements Client {

    @Override
    @ABTest(methodName = "queryB")
    public String query(String p1, Integer p2) {
        return this.getClass().getName() + ".query";
    }

    @Override
    public String queryB(String p1, Integer p2) {
        return this.getClass().getName() + ".queryB";
    }

    @Override
    @ABTest
    public String query2(String p1, Integer p2) {
        return this.getClass().getName() + ".query2";
    }

    @Override
    public String query2B(String p1, Integer p2) {
        return this.getClass().getName() + ".query2B";
    }
}

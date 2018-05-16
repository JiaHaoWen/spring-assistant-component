package com.github.jiahaowen.spring.assistant.component.migration.abtest.core.client;

/**
 * @author jiahaowen
 * @version $Id: Client.java, v 0.1 16/11/22 下午3:55 jiahaowen Exp $
 */
public interface Client {
    String query(String p1, Integer p2);

    String queryB(String p1, Integer p2);

    String query2(String p1, Integer p2);

    String query2B(String p1, Integer p2);
}

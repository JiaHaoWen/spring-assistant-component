package com.github.jiahaowen.spring.assistant.component.migration.diff;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * @author chuanmu
 * @since 2018/5/15
 */
public class DifferBuilderTest {

    @Test
    public void testEquals() {

        try {
            Person p1 = new Person("11", "test");
            Person p2 = new Person("11", "test");

            boolean isEquals = DifferBuilder.build().isEqual(p1, p2);
            assertTrue(isEquals);

        } catch (Throwable t) {
            assertTrue(false);
        }
    }

    @Test
    public void testNotEquals() {

        try {
            Person p1 = new Person("11", "test");
            Person p2 = new Person("12", "test");

            boolean isEquals = DifferBuilder.build().isEqual(p1, p2);
            assertTrue(!isEquals);

        } catch (Throwable t) {
            assertTrue(true);
        }
    }
}

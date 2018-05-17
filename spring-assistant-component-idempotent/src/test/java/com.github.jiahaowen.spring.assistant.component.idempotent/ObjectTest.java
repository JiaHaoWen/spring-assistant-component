package com.github.jiahaowen.spring.assistant.component.idempotent;

/**
 * @author jiahaowen
 * @date 2017/8/1 16:41
 */
public class ObjectTest {
    private TestA testA;

    public TestA getTestA() {
        return new TestA();
    }

    class TestA {
        private TestB testB;

        public TestB getTestB() {
            return new TestB();
        }
    }

    class TestB {
        private String name = "wodiu";
        private int age = 1;

        public String getName() {
            return this.name;
        }

        public int getAge() {
            return age;
        }
    }
}

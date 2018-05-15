package com.github.jiahaowen.spring.assistant.component.migration.diff;

/**
 * @author chuanmu
 * @since 2018/5/15
 */
public class Person {
    private String age;

    private String name;

    public Person() {}

    public Person(String age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

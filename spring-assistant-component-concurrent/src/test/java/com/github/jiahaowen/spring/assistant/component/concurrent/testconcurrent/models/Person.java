package com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent.models;


import com.github.jiahaowen.spring.assistant.component.concurrent.internal.models.BasicOption;

/**
 * @author chuanmu
 * @since 2017/11/20
 */
public class Person extends BasicOption {

  private String name;

  private String age;

  public Person(String name, String age, String unqueId) {
    this.name = name;
    this.age = age;
    this.uniqueId = unqueId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }
}

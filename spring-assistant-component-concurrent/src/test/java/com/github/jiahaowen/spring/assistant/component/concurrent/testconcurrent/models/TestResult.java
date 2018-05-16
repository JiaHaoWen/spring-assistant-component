package com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent.models;

import java.io.Serializable;

/**
 * @author chuanmu
 * @since 2017/11/20
 */
public class TestResult implements Serializable {

  private static final long serialVersionUID = 5556800982709691087L;

  private String result;

  public TestResult() {}

  public TestResult(String result) {
    this.result = result;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}

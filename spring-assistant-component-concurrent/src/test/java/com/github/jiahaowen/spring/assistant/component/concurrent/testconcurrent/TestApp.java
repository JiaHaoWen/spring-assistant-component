package com.github.jiahaowen.spring.assistant.component.concurrent.testconcurrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * @author chuanmu
 * @since 2017/11/21
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@ImportResource("classpath*:/spring/*.xml")
public class TestApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TestApp.class);
        springApplication.setWebEnvironment(false);
        springApplication.run(args);
    }
}

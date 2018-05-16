package com.github.jiahaowen.spring.assistant.component.migration.abtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * @author jiahaowen
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

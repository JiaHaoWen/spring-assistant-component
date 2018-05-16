package com.github.jiahaowen.spring.assistant.component.cache.spring;

import com.github.jiahaowen.spring.assistant.component.cache.spring.condition.UserCondition;
import com.github.jiahaowen.spring.assistant.component.cache.spring.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.PageRequest;

@SpringBootApplication
@MapperScan("com.github.jiahaowen.spring.assistant.component.cache.spring.mapper")
@EnableAspectJAutoProxy
public class CacheDemoApplication {

    public static void main(String[] args) {
        configureApplication(new SpringApplicationBuilder()).run(args);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        ApplicationListener<ApplicationReadyEvent> readyListener =
                new ApplicationListener<ApplicationReadyEvent>() {

                    @Override
                    public void onApplicationEvent(ApplicationReadyEvent event) {
                        ConfigurableApplicationContext context = event.getApplicationContext();
                        UserMapper userMapper = context.getBean(UserMapper.class);
                        userMapper.allUsers();

                        UserCondition condition = new UserCondition();
                        PageRequest page = new PageRequest(1, 10);
                        condition.setPageable(page);
                        condition.setStatus(1);
                        userMapper.listByCondition(condition);
                    }
                };
        return builder.sources(CacheDemoApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .listeners(readyListener);
    }
}

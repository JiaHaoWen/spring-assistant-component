package com.github.jiahaowen.spring.assistant.component.migration.abtest;

import com.github.jiahaowen.spring.assistant.component.migration.internal.SpringAssistantComponentServiceMigrationConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author jiahaowen
 * @since 2017/11/20
 */
@SpringBootTest(classes = TestApp.class)
@ContextConfiguration(
    classes = {SpringAssistantComponentServiceMigrationConfig.class},
    locations = {"classpath*:/spring/*.xml"}
)
public abstract class MigrationTestBase extends AbstractTestNGSpringContextTests {}

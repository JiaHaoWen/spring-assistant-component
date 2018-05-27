package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

/**
 * @author jiahaowen.jhw
 * @version $Id: CustomGroovyClassLoaderFactory.java, v 0.1 2016-12-01 下午5:45 jiahaowen.jhw Exp $
 */
public class CustomGroovyClassLoaderFactory {

    public static CompilerConfiguration config = CompilerConfiguration.DEFAULT;

    public static ImportCustomizer importCustomizer = new ImportCustomizer();

    static {
        importCustomizer.addImports("groovy.transform.Field");
        importCustomizer.addStaticStars("rule.engine.demo.model.Rule");
        config.addCompilationCustomizers(importCustomizer);
    }

    // 初始化 groovyclassloader
    public static GroovyClassLoader newClassLoaderWithImplictImport() {
        return new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
    }
}

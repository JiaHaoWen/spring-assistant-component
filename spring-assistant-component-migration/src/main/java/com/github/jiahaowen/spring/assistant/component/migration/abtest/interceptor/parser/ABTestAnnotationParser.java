package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.parser;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.ABTest;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.checker.Checker;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation.ABTestOperation;
import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.splitter.Splitter;
import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ABTest注解解析器
 *
 * @author jiahaowen
 * @version $Id: ABTestAnnotationParser.java, v 0.1 16/11/22 下午2:34 jiahaowen Exp $
 */
@Component
public class ABTestAnnotationParser implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ABTestAnnotationParser.class);

    private ApplicationContext applicationContext;

    public List<ABTestOperation> parseCacheAnnotations(AnnotatedElement ae) {
        List<ABTestOperation> ops = null;

        List<ABTest> abTests = getAnnotations(ae, ABTest.class);
        if (abTests != null) {
            ops = lazyInit(ops);
            for (ABTest abTest : abTests) {
                ops.add(parseABTestAnnotation(abTest));
            }
        }
        return ops;
    }

    private ABTestOperation parseABTestAnnotation(ABTest abTest) {

        try {
            ABTestOperation cuo = new ABTestOperation();
            cuo.setMethodName(abTest.methodName());
            final String checker = abTest.checker();
            cuo.setChecker(applicationContext.getBean(checker, Checker.class));
            final String splitter = abTest.splitter();
            cuo.setSplitter(applicationContext.getBean(splitter, Splitter.class));
            return cuo;
        } catch (Throwable e) {
            logger.error("parseABTestAnnotation failed", e);
            throw new RuntimeException("Not Found Checker/Splitter Bean,params:" + abTest, e);
        }
    }

    private <T extends Annotation> List<T> getAnnotations(
            AnnotatedElement ae, Class<T> annotationType) {
        List<T> annotations = Lists.newArrayList();

        // look at raw annotation
        T ann = ae.getAnnotation(annotationType);
        if (ann != null) {
            annotations.add(ann);
        }

        // scan meta-annotations
        for (Annotation metaAnn : ae.getAnnotations()) {
            ann = metaAnn.annotationType().getAnnotation(annotationType);
            if (ann != null) {
                annotations.add(ann);
            }
        }

        return (annotations.isEmpty() ? null : annotations);
    }

    private List<ABTestOperation> lazyInit(List<ABTestOperation> ops) {
        return (ops != null ? ops : Lists.<ABTestOperation>newArrayList());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

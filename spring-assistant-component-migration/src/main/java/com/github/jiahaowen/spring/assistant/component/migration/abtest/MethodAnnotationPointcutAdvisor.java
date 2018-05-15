package com.github.jiahaowen.spring.assistant.component.migration.abtest;

import java.lang.annotation.Annotation;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;

/**
 * PointcutAdvisor
 *
 * @author jiahaowen
 */
@Component
public class MethodAnnotationPointcutAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = -7431399745989513294L;

    private final Pointcut pointcut;

    private final Advice advice;

    public MethodAnnotationPointcutAdvisor(
            Class<? extends Annotation> methodAnnotationType, Advice advice) {
        this.pointcut = new AnnotationMatchingPointcut(null, methodAnnotationType);
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}

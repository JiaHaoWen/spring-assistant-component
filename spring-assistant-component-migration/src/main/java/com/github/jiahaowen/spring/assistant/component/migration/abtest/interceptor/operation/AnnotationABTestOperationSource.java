package com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.operation;

import com.github.jiahaowen.spring.assistant.component.migration.abtest.interceptor.parser.ABTestAnnotationParser;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

/**
 * ABTest注解解析器
 *
 * @author jiahaowen
 * @version $Id: AnnotationABTestOperationSource.java, v 0.1 16/11/22 下午2:31 jiahaowen Exp $
 */
@Component
public class AnnotationABTestOperationSource implements ABTestOperationSource, Serializable {

    @Autowired private ABTestAnnotationParser abTestAnnotationParser;

    @Override
    public ABTestOperation getABTestOperation(Method method, Class<?> targetClass) {
        final List<ABTestOperation> abTestOperations = computeCacheOperations(method, targetClass);
        if (CollectionUtils.isEmpty(abTestOperations)) {
            return null;
        }
        return abTestOperations.get(0);
    }

    private List<ABTestOperation> computeCacheOperations(Method method, Class<?> targetClass) {
        // 检查是否只允许公开的方法
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        // First try is the method in the target class.
        List<ABTestOperation> opDef = findABTestOperations(specificMethod);
        if (opDef != null) {
            return opDef;
        }

        // Second try is the ab test operation on the target class.
        opDef = findABTestOperations(specificMethod.getDeclaringClass());
        if (opDef != null) {
            return opDef;
        }

        if (specificMethod != method) {
            // Fall back is to look at the original method.
            opDef = findABTestOperations(method);
            if (opDef != null) {
                return opDef;
            }
            // Last fall back is the class of the original method.
            return findABTestOperations(method.getDeclaringClass());
        }
        return null;
    }

    protected boolean allowPublicMethodsOnly() {
        return false;
    }

    protected List<ABTestOperation> findABTestOperations(Class<?> clazz) {
        return determineABTestOperations(clazz);
    }

    protected List<ABTestOperation> findABTestOperations(Method method) {
        return determineABTestOperations(method);
    }

    protected List<ABTestOperation> determineABTestOperations(AnnotatedElement ae) {
        final List<ABTestOperation> ops = Lists.newArrayList();
        final List<ABTestOperation> annOps = abTestAnnotationParser.parseCacheAnnotations(ae);
        if (!CollectionUtils.isEmpty(annOps)) {
            ops.addAll(annOps);
        }
        return ops;
    }
}

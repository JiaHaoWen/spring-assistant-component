package com.github.jiahaowen.spring.assistant.component.idempotent.key;

import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.Idempotent;
import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.IdempotentParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * @author jiahaowen
 */
@Component
public class DefaultIdempotentKey implements IdempotentKeyStrategy {
    @Override
    public String key(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        if (idempotent.previous().isEmpty() && invocation.getArguments().length == 0) {
            throw new IllegalArgumentException(
                    "idempotent must set previous key or has IdempotentParam value");
        }
        Annotation[][] annotations = method.getParameterAnnotations();
        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getName())
                .append(".")
                .append(method.getName())
                .append("-");
        for (int i = 0, iLen = annotations.length; i < iLen; i++) {
            for (int j = 0, jLen = annotations[i].length; j < jLen; j++) {
                Annotation annotation = annotations[i][j];
                if (annotation.annotationType().isAssignableFrom(IdempotentParam.class)) {
                    sb.append(extractValueFromParam(annotation, invocation.getArguments()[i]));
                }
            }
        }
        return idempotent.previous().isEmpty()
                ? sb.toString()
                : idempotent.previous() + "-" + sb.toString();
    }

    private String extractValueFromParam(Annotation annotation, Object object) {
        IdempotentParam param = (IdempotentParam) annotation;
        String[] value = param.value();
        StringBuilder sb = new StringBuilder();
        for (String v : value) {
            sb.append(v.contains(".") ? extractValueFromObject(v, object) : String.valueOf(object))
                    .append("-");
        }
        return sb.toString();
    }

    private String extractValueFromObject(String value, Object object) {
        Object currentObject = object;
        for (String s : value.split("\\.")) {
            try {
                Method method =
                        currentObject
                                .getClass()
                                .getMethod(
                                        "get"
                                                + Character.toUpperCase(s.charAt(0))
                                                + s.substring(1));
                method.setAccessible(true);
                currentObject = method.invoke(currentObject);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new IllegalStateException(
                        String.format(
                                "IdempotentParam 定义的value值[%s],在对象[%s]中不存在，请检查是否设置了get方法",
                                s, currentObject.getClass()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalStateException("当前执行的方法没有访问权限:" + e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new IllegalStateException("执行当前方法报错：" + e);
            }
        }
        return String.valueOf(currentObject);
    }
}

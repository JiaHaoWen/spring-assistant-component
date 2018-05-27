package com.github.jiahaowen.spring.assistant.component.rule.api.impl;

import com.github.jiahaowen.spring.assistant.component.rule.exception.FuncNotFoundException;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.MetaClassImpl;
import groovy.lang.Tuple;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.PogoMetaClassSite;
import org.springframework.stereotype.Component;

/**
 * groovy元数据解析
 *
 * @author jiahaowen.jhw
 * @version $Id: CustomMetaClassImpl.java, v 0.1 2016-12-01 下午8:20 jiahaowen.jhw Exp $
 */
@Component
public class CustomMetaClassImpl extends MetaClassImpl {

    public CustomMetaClassImpl(Class scriptClass) {
        super(scriptClass);
        this.initialize();
    }

    public Object tryGetFromBinding(String methodName) {
        Binding binding = BindingContextHolder.getBinding();

        // 优先从Binding里查找
        if (binding.hasVariable(methodName)) {
            return binding.getVariable(methodName);
        }

        return null;
    }

    // TODO:

    @Override
    public Object invokeMethod(Object object, String methodName, Object[] originalArguments) {

        return this.invokeMethod(object, methodName, (Object) originalArguments);
    }

    @Override
    public Object invokeMethod(Object object, String methodName, Object arguments) {
        // 全局的闭包、往上下文里放的java method等的调用会走到这里
        // class里的闭包的方法，不会走到这里，会生成一个新的class，metaclass为org.codehaus.groovy.runtime.metaclass.ClosureMetaClass
        // 【重要】只有在调用那些class里面没有定义的方法才会走到这里，class里定义的闭包、def等，都直接生成了class的method，不会走到这里

        if (object == null) {
            throw new NullPointerException(
                    "Cannot invoke method: " + methodName + " on null object");
        }

        Object[] args = null;

        // 调整参数
        if (arguments == null) {
            args = MetaClassHelper.EMPTY_ARRAY;
        }
        if (arguments instanceof Tuple) {
            Tuple tuple = (Tuple) arguments;
            args = tuple.toArray();
        }
        if (arguments instanceof Object[]) {
            args = (Object[]) arguments;
        } else {
            args = new Object[] {arguments};
        }

        // 优先从上下文中加载
        Object v = tryGetFromBinding(methodName);
        if (v != null) {
            // 闭包
            if (v instanceof Closure) {

                Closure<?> closure = ((Closure<?>) v);

                return closure.getMetaClass().invokeMethod(closure, "doCall", args);

            } else if (v instanceof Method) {
                // 反射
                Method m = (Method) v;
                try {

                    return m.invoke(object, args);

                } catch (Throwable e) {
                    if (e instanceof InvocationTargetException) {
                        InvocationTargetException invocationEx = (InvocationTargetException) e;
                        Throwable targetEx = invocationEx.getTargetException();
                        if (targetEx instanceof RuntimeException) throw (RuntimeException) targetEx;
                        else throw new RuntimeException(targetEx);
                    }
                }
            }

            return v;
        }
        // "f_"开头的是函数，如果上面找不到，直接抛异常，避免再从上下文里找，耗费性能。
        if (methodName.startsWith("f_")) {
            throw new FuncNotFoundException(
                    "No method ["
                            + methodName
                            + "（）] founded; please check the function definition. 找不到对应的groovy函数定义： ["
                            + methodName
                            + "（）,请检查函数库]");
        }

        // 那些真正找不到的、未定义的方法才能走到这里，class自定义的method不会走到这里
        return this.invokeMethod(theClass, object, methodName, args, false, false);
    }

    /** 脚本、类里自定义的method */
    @Override
    protected Object chooseMethod(
            String methodName,
            Object methodOrList,
            @SuppressWarnings("rawtypes") Class[] arguments) {
        return super.chooseMethod(methodName, methodOrList, arguments);
    }

    /** 方法和闭包 */
    @SuppressWarnings("rawtypes")
    @Override
    public CallSite createPogoCallCurrentSite(CallSite site, Class sender, Object[] args) {
        String methodName = site.getName();

        // 优先从上下文加载
        Object v = tryGetFromBinding(methodName);
        if (v != null) {
            if (v instanceof Closure) {
                return new PogoMetaClassSite(site, this);
            }
        }
        return super.createPogoCallCurrentSite(site, sender, args);
    }
}

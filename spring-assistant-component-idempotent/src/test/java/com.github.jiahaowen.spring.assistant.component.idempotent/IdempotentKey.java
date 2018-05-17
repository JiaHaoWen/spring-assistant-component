package com.github.jiahaowen.spring.assistant.component.idempotent;

import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.Idempotent;
import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.IdempotentParam;
import com.github.jiahaowen.spring.assistant.component.idempotent.data.IdempotentOperateUtil;
import com.github.jiahaowen.spring.assistant.component.idempotent.interceptor.IdempotentInterceptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.Order;

/**
 * @author jiahaowen
 * @date 2017/7/31 17:52
 */
public class IdempotentKey {
    @Idempotent(
        previous = "uniqueKey",
        expiredTime = 20,
        returnType = String.class,
        returnValue = "test"
    )
    public void businessWithinIdempotent(
            @IdempotentParam String uniqueKey,
            @IdempotentParam(value = "name") Order order) {
        // business code within idempotent
    }

    public String businessWithinIdempotent() {
        String key = "uniqueKey1";
        if (!IdempotentOperateUtil.proceed(key, 20)) {
            return null;
        }
        try {
            // do business code within idempotent
            return "success";
        } catch (Throwable throwable) {
            // 如果业务操作失败后，则需要执行回滚操作，防止后续相同的业务无法操作
            IdempotentOperateUtil.callBack(key);
            throw throwable;
        }
    }

    public Object interceptor() throws Throwable {
        IdempotentInterceptor interceptor = new IdempotentInterceptor();
        return interceptor.invoke(
                new MethodInvocation() {
                    @Override
                    public Method getMethod() {
                        try {
                            return IdempotentKey.class.getMethod(
                                    "idempotentMethod", ObjectTest.class, String.class);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public Object[] getArguments() {
                        return new Object[] {new ObjectTest(), "ssssssss"};
                    }

                    @Override
                    public Object proceed() throws Throwable {
                        return "success";
                    }

                    @Override
                    public Object getThis() {
                        return this;
                    }

                    @Override
                    public AccessibleObject getStaticPart() {
                        return null;
                    }
                });
    }

    @Idempotent(previous = "www333", expiredTime = 10)
    public void idempotentMethod(
            @IdempotentParam(value = {"testA.testB.name", "testA.testB.age"})
                    ObjectTest objectTest,
            @IdempotentParam String haha) {}

    public static void main(String[] args)
            throws InterruptedException, NoSuchMethodException, InvocationTargetException,
                    IllegalAccessException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        // System.out.println(new IdempotentKey().businessWithinIdempotent());
                        try {
                            System.out.println(new IdempotentKey().interceptor());
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });

        executorService.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        // System.out.println(new IdempotentKey().businessWithinIdempotent());
                        try {
                            System.out.println(new IdempotentKey().interceptor());
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}

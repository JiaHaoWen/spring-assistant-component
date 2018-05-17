package com.github.jiahaowen.spring.assistant.component.idempotent;

import com.github.jiahaowen.spring.assistant.component.idempotent.annotation.Idempotent;
import com.github.jiahaowen.spring.assistant.component.idempotent.key.DefaultReturnValue;
import java.lang.reflect.Method;

/**
 * @author jiahaowen
 * @date 2017/9/13 9:37
 */
public class DefaultReturnValueTest {
    public static void main(String[] args) {
        DefaultReturnValue returnValue = new DefaultReturnValue();
        try {
            Method m = DefaultReturnValueTest.class.getMethod("custom");
            ObjectTest ot = (ObjectTest) returnValue.value(m.getAnnotation(Idempotent.class));
            System.out.println(ot.getTestA());

            Method m2 = DefaultReturnValueTest.class.getMethod("primitive2");
            System.out.println(returnValue.value(m2.getAnnotation(Idempotent.class)));

            Method m3 = DefaultReturnValueTest.class.getMethod("primitive3");
            System.out.println(returnValue.value(m3.getAnnotation(Idempotent.class)));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Idempotent(returnType = ObjectTest.class, returnValue = "1")
    public void custom() {}

    @Idempotent(returnType = String.class, returnValue = "1222")
    public void primitive2() {}

    @Idempotent(returnType = String.class)
    public void primitive3() {}
}

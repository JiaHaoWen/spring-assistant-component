package com.github.jiahaowen.spring.assistant.component.cache.test.hessian;

import com.github.jiahaowen.spring.assistant.component.cache.serializer.HessianSerializer;
import com.github.jiahaowen.spring.assistant.component.cache.serializer.hession.WeakReferenceSerializerFactory;
import com.github.jiahaowen.spring.assistant.component.cache.test.Simple;
import java.lang.ref.WeakReference;

/**
 * @author: jiahaowen
 */
public class WeakReferenceTest {

    private static final HessianSerializer SERIALIZER=new HessianSerializer();

    static {
        SERIALIZER.addSerializerFactory(new WeakReferenceSerializerFactory());
    }

    public static void main(String[] args) {
        Simple s=new Simple();
        s.setName("tt");
        s.setAge(10);
        s.setSex(1);
        WeakReference<Simple> ref=new WeakReference<Simple>(s);
        s=null;
        try {
            WeakReference<Simple> ref2=(WeakReference<Simple>)SERIALIZER.deepClone(ref, null);
            System.out.println(ref2.get());
        } catch(Exception e1) {
            e1.printStackTrace();
        }
        int i=0;
        while(ref.get() != null) {
            System.out.println(String.format("Get str from object of WeakReference: %s, count: %d", ref.get(), ++i));
            if(i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {

            }
        }
        System.out.println("object a was cleared by JVM!");
        try {
            WeakReference<Simple> ref2=(WeakReference<Simple>)SERIALIZER.deepClone(ref, null);
            System.out.println(ref2.get());
        } catch(Exception e1) {
            e1.printStackTrace();
        }
    }

}

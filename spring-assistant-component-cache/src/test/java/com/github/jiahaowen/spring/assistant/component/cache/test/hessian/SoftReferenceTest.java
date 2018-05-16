package com.github.jiahaowen.spring.assistant.component.cache.test.hessian;


import com.github.jiahaowen.spring.assistant.component.cache.test.Simple;
import java.lang.ref.SoftReference;

/**
 * @author: jiahaowen
 */
public class SoftReferenceTest {

    public static void main(String[] args) {
        Simple s=new Simple();
        s.setName("tt");
        s.setAge(10);
        s.setSex(1);
        SoftReference<Simple> ref=new SoftReference<Simple>(s);
        s=null;
        int i=0;
        while(ref.get() != null) {
            System.out.println(String.format("Get str from object of SoftReference: %s, count: %d", ref.get(), ++i));
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
    }

}

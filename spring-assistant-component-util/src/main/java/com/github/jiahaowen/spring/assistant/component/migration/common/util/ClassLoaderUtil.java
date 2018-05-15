/** WaCai Inc. Copyright (c) 2009-2017 All Rights Reserved. */
package com.github.jiahaowen.spring.assistant.component.migration.common.util;

/**
 * 类加载器工具类
 *
 * @author chuanmu
 * @since 2017/11/29
 */
public class ClassLoaderUtil {

    /* ============================================================================ */
    /*  取得context class loader的方法。                                            */
    /* ============================================================================ */

    /**
     * 取得当前线程的<code>ClassLoader</code>。这个功能需要JDK1.2或更高版本的JDK的支持。
     *
     * @return 当前线程的<code>ClassLoader</code>
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /* ============================================================================ */
    /*  装入类的方法。                                                              */
    /* ============================================================================ */

    /**
     * 从当前线程的<code>ClassLoader</code>装入类。对于JDK1.2以下，则相当于<code>Class.forName</code>。
     *
     * @param className 要装入的类名
     * @return 已装入的类
     * @throws ClassNotFoundException 如果类没找到
     */
    public static Class loadClass(String className) throws ClassNotFoundException {
        return loadClass(className, getContextClassLoader());
    }

    /**
     * 从指定的<code>ClassLoader</code>中装入类。如果未指定<code>ClassLoader</code>， 则从装载<code>ClassLoaderUtil
     * </code>的<code>ClassLoader</code>中装入。
     *
     * @param className 要装入的类名
     * @param classLoader 从指定的<code>ClassLoader</code>中装入类，如果为<code>null</code>，表示从<code>
     *     ClassLoaderUtil</code>所在的class loader中装载
     * @return 已装入的类
     * @throws ClassNotFoundException 如果类没找到
     */
    public static Class loadClass(String className, ClassLoader classLoader)
            throws ClassNotFoundException {
        if (className == null) {
            return null;
        }

        if (classLoader == null) {
            return Class.forName(className);
        } else {
            return Class.forName(className, true, classLoader);
        }
    }
}

package com.github.jiahaowen.spring.assistant.component.util.common.util;

import java.io.Serializable;

/**
 * 有关<code>Object</code>处理的工具类。
 *
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 *
 * @author chuanmu
 * @since 2017/11/29
 */
public class ObjectUtil {
    /* ============================================================================ */
    /*  常量和singleton。                                                           */
    /* ============================================================================ */

    /**
     * 用于表示<code>null</code>的常量。
     *
     * <p>例如，<code>HashMap.get(Object)</code>方法返回<code>null</code>有两种可能： 值不存在或值为<code>null</code>
     * 。而这个singleton可用来区别这两种情形。
     *
     * <p>另一个例子是，<code>Hashtable</code>的值不能为<code>null</code>。
     */
    public static final Object NULL =
            new Serializable() {
                private static final long serialVersionUID = 7092611880189329093L;

                private Object readResolve() {
                    return NULL;
                }
            };

    /* ============================================================================ */
    /*  默认值函数。                                                                */
    /*                                                                              */
    /*  当对象为null时，将对象转换成指定的默认对象。                                */
    /* ============================================================================ */

    /**
     * 如果对象为<code>null</code>，则返回指定默认对象，否则返回对象本身。
     *
     * <pre>
     * ObjectUtil.defaultIfNull(null, null)      = null
     * ObjectUtil.defaultIfNull(null, "")        = ""
     * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtil.defaultIfNull("abc", *)        = "abc"
     * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param object 要测试的对象
     * @param defaultValue 默认值
     * @return 对象本身或默认对象
     */
    public static Object defaultIfNull(Object object, Object defaultValue) {
        return (object != null) ? object : defaultValue;
    }

    /* ============================================================================ */
    /*  比较函数。                                                                  */
    /*                                                                              */
    /*  以下方法用来比较两个对象是否相同。                                          */
    /* ============================================================================ */

    /**
     * 比较两个对象是否完全相等。
     *
     * <p>此方法可以正确地比较多维数组。
     *
     * <pre>
     * ObjectUtil.equals(null, null)                  = true
     * ObjectUtil.equals(null, "")                    = false
     * ObjectUtil.equals("", null)                    = false
     * ObjectUtil.equals("", "")                      = true
     * ObjectUtil.equals(Boolean.TRUE, null)          = false
     * ObjectUtil.equals(Boolean.TRUE, "true")        = false
     * ObjectUtil.equals(Boolean.TRUE, Boolean.TRUE)  = true
     * ObjectUtil.equals(Boolean.TRUE, Boolean.FALSE) = false
     * </pre>
     *
     * @param object1 对象1
     * @param object2 对象2
     * @return 如果相等, 则返回<code>true</code>
     */
    public static boolean equals(Object object1, Object object2) {
        return ArrayUtil.equals(object1, object2);
    }

    /* ============================================================================ */
    /*  Hashcode函数。                                                              */
    /*                                                                              */
    /*  以下方法用来取得对象的hash code。                                           */
    /* ============================================================================ */

    /**
     * 取得对象的hash值, 如果对象为<code>null</code>, 则返回<code>0</code>。
     *
     * <p>此方法可以正确地处理多维数组。
     *
     * @param object 对象
     * @return hash值
     */
    public static int hashCode(Object object) {
        return ArrayUtil.hashCode(object);
    }

    /**
     * 取得对象的原始的hash值, 如果对象为<code>null</code>, 则返回<code>0</code>。
     *
     * <p>该方法使用<code>System.identityHashCode</code>来取得hash值，该值不受对象本身的<code>hashCode</code>方法的影响。
     *
     * @param object 对象
     * @return hash值
     */
    public static int identityHashCode(Object object) {
        return (object == null) ? 0 : System.identityHashCode(object);
    }

    /* ============================================================================ */
    /*  取得对象的identity。                                                        */
    /* ============================================================================ */

    /**
     * 取得对象自身的identity，如同对象没有覆盖<code>toString()</code>方法时，<code>Object.toString()</code>的原始输出。
     *
     * <pre>
     * ObjectUtil.identityToString(null)          = null
     * ObjectUtil.identityToString("")            = "java.lang.String@1e23"
     * ObjectUtil.identityToString(Boolean.TRUE)  = "java.lang.Boolean@7fa"
     * ObjectUtil.identityToString(new int[0])    = "int[]@7fa"
     * ObjectUtil.identityToString(new Object[0]) = "java.lang.Object[]@7fa"
     * </pre>
     *
     * @param object 对象
     * @return 对象的identity，如果对象是<code>null</code>，则返回<code>null</code>
     */
    public static String identityToString(Object object) {
        if (object == null) {
            return null;
        }

        return appendIdentityToString(null, object).toString();
    }

    /**
     * 取得对象自身的identity，如同对象没有覆盖<code>toString()</code>方法时，<code>Object.toString()</code>的原始输出。
     *
     * <pre>
     * ObjectUtil.identityToString(null, "NULL")            = "NULL"
     * ObjectUtil.identityToString("", "NULL")              = "java.lang.String@1e23"
     * ObjectUtil.identityToString(Boolean.TRUE, "NULL")    = "java.lang.Boolean@7fa"
     * ObjectUtil.identityToString(new int[0], "NULL")      = "int[]@7fa"
     * ObjectUtil.identityToString(new Object[0], "NULL")   = "java.lang.Object[]@7fa"
     * </pre>
     *
     * @param object 对象
     * @param nullStr 如果对象为<code>null</code>，则返回该字符串
     * @return 对象的identity，如果对象是<code>null</code>，则返回指定字符串
     */
    public static String identityToString(Object object, String nullStr) {
        if (object == null) {
            return nullStr;
        }

        return appendIdentityToString(null, object).toString();
    }

    /**
     * 将对象自身的identity——如同对象没有覆盖<code>toString()</code>方法时，<code>Object.toString()</code>的原始输出——追加到
     * <code>StringBuffer</code>中。
     *
     * <pre>
     * ObjectUtil.appendIdentityToString(*, null)            = null
     * ObjectUtil.appendIdentityToString(null, "")           = "java.lang.String@1e23"
     * ObjectUtil.appendIdentityToString(null, Boolean.TRUE) = "java.lang.Boolean@7fa"
     * ObjectUtil.appendIdentityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
     * ObjectUtil.appendIdentityToString(buf, new int[0])    = buf.append("int[]@7fa")
     * ObjectUtil.appendIdentityToString(buf, new Object[0]) = buf.append("java.lang.Object[]@7fa")
     * </pre>
     *
     * @param buffer <code>StringBuffer</code>对象，如果是<code>null</code>，则创建新的
     * @param object 对象
     * @return <code>StringBuffer</code>对象，如果对象为<code>null</code>，则返回<code>null</code>
     */
    public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
        if (object == null) {
            return null;
        }

        if (buffer == null) {
            buffer = new StringBuffer();
        }

        buffer.append(ClassUtil.getClassNameForObject(object));

        return buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
    }

    /* ============================================================================ */
    /*  比较对象的类型。                                                            */
    /* ============================================================================ */

    /**
     * 检查两个对象是否属于相同类型。<code>null</code>将被看作任意类型。
     *
     * @param object1 对象1
     * @param object2 对象2
     * @return 如果两个对象有相同的类型，则返回<code>true</code>
     */
    public static boolean isSameType(Object object1, Object object2) {
        if ((object1 == null) || (object2 == null)) {
            return true;
        }

        return object1.getClass().equals(object2.getClass());
    }

    /* ============================================================================ */
    /*  toString方法。                                                              */
    /* ============================================================================ */

    /**
     * 取得对象的<code>toString()</code>的值，如果对象为<code>null</code>，则返回空字符串<code>""</code>。
     *
     * <pre>
     * ObjectUtil.toString(null)         = ""
     * ObjectUtil.toString("")           = ""
     * ObjectUtil.toString("bat")        = "bat"
     * ObjectUtil.toString(Boolean.TRUE) = "true"
     * ObjectUtil.toString([1, 2, 3])    = "[1, 2, 3]"
     * </pre>
     *
     * @param object 对象
     * @return 对象的<code>toString()</code>的返回值，或空字符串<code>""</code>
     */
    public static String toString(Object object) {
        return (object == null)
                ? StringUtil.EMPTY_STRING
                : (object.getClass().isArray() ? ArrayUtil.toString(object) : object.toString());
    }

    /**
     * 取得对象的<code>toString()</code>的值，如果对象为<code>null</code>，则返回指定字符串。
     *
     * <pre>
     * ObjectUtil.toString(null, null)           = null
     * ObjectUtil.toString(null, "null")         = "null"
     * ObjectUtil.toString("", "null")           = ""
     * ObjectUtil.toString("bat", "null")        = "bat"
     * ObjectUtil.toString(Boolean.TRUE, "null") = "true"
     * ObjectUtil.toString([1, 2, 3], "null")    = "[1, 2, 3]"
     * </pre>
     *
     * @param object 对象
     * @param nullStr 如果对象为<code>null</code>，则返回该字符串
     * @return 对象的<code>toString()</code>的返回值，或指定字符串
     */
    public static String toString(Object object, String nullStr) {
        return (object == null)
                ? nullStr
                : (object.getClass().isArray() ? ArrayUtil.toString(object) : object.toString());
    }
}

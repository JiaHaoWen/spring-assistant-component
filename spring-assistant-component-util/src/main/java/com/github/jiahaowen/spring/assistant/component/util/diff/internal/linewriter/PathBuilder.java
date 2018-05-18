package com.github.jiahaowen.spring.assistant.component.util.diff.internal.linewriter;

/**
 * 差异路径构建工具类
 *
 * @author jiahaowen.jhw
 * @version $Id: PathBuilder.java, v 0.1 2016-10-30 下午9:38 jiahaowen.jhw Exp $
 */
public final class PathBuilder {

    // key/value分隔符
    private static final String KEY_VALUE_SEPARATOR = "=";
    // 路径层级关系分隔符
    private static final String PATH_SEPARATOR = ".";
    // 迭代对象起始位置符
    private static final String INDEX_ENCLOSER_START = "[";
    // 迭代对象结束位置符
    private static final String INDEX_ENCLOSER_END = "]";

    /**
     * 对指定路径追加属性名称,得到完整的对象层级关系
     *
     * @param path
     * @param propertyName
     * @return
     */
    public static String extendPathWithProperty(final String path, final String propertyName) {
        return new StringBuilder()
                .append(path)
                .append(PATH_SEPARATOR)
                .append(propertyName)
                .toString();
    }

    /**
     * 对指定路径追加索引序号,得到完整的iterable路径
     *
     * @param path
     * @param index
     * @return
     */
    public static String extendPathWithIterableIndex(final String path, final int index) {
        return new StringBuilder()
                .append(path)
                .append(INDEX_ENCLOSER_START)
                .append(index)
                .append(INDEX_ENCLOSER_END)
                .toString();
    }

    /**
     * 对指定的路径追加对象索引,得到map路径
     *
     * @param path
     * @param key
     * @return
     */
    public static String extendPathWithMapIndex(final String path, final Object key) {
        return new StringBuilder()
                .append(path)
                .append(INDEX_ENCLOSER_START)
                .append(key)
                .append(INDEX_ENCLOSER_END)
                .toString();
    }

    /**
     * 对指定的路径追加对象值索引,得到完整的key/value路径
     *
     * @param path
     * @param value
     * @return
     */
    public static String extendPathWithValue(final String path, final Object value) {
        return new StringBuilder()
                .append(path)
                .append(KEY_VALUE_SEPARATOR)
                .append(value)
                .toString();
    }
}

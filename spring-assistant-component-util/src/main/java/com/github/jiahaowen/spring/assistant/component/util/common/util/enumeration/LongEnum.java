package com.github.jiahaowen.spring.assistant.component.util.common.util.enumeration;

/**
 * 类型安全的枚举类型, 代表一个长整数.
 *
 * @author chuanmu
 * @since 2017/11/29
 */
public abstract class LongEnum extends Enum {
    private static final long serialVersionUID = 8152633183977823349L;

    /**
     * 创建一个枚举量.
     *
     * @param value 枚举量的整数值
     */
    protected static final LongEnum create(long value) {
        return (LongEnum) createEnum(new Long(value));
    }

    /**
     * 创建一个枚举量.
     *
     * @param value 枚举量的整数值
     */
    protected static final LongEnum create(Number value) {
        return (LongEnum) createEnum(new Long(value.longValue()));
    }

    /**
     * 创建一个枚举量.
     *
     * @param name 枚举量的名称
     * @param value 枚举量的整数值
     */
    protected static final LongEnum create(String name, long value) {
        return (LongEnum) createEnum(name, new Long(value));
    }

    /**
     * 创建一个枚举量.
     *
     * @param name 枚举量的名称
     * @param value 枚举量的整数值
     */
    protected static final LongEnum create(String name, Number value) {
        return (LongEnum) createEnum(name, new Long(value.longValue()));
    }

    /**
     * 创建一个枚举类型的<code>EnumType</code>.
     *
     * @return 枚举类型的<code>EnumType</code>
     */
    protected static Object createEnumType() {
        return new EnumType() {
            @Override
            protected Class getUnderlyingClass() {
                return Long.class;
            }

            @Override
            protected Number getNextValue(Number value, boolean flagMode) {
                if (value == null) {
                    return flagMode ? new Long(1) : new Long(0); // 默认起始值
                }

                long longValue = ((Long) value).longValue();

                if (flagMode) {
                    return new Long(longValue << 1); // 位模式
                } else {
                    return new Long(longValue + 1);
                }
            }

            @Override
            protected boolean isZero(Number value) {
                return ((Long) value).longValue() == 0L;
            }
        };
    }

    /**
     * 实现<code>Number</code>类, 取得整数值.
     *
     * @return 整数值
     */
    @Override
    public int intValue() {
        return ((Long) getValue()).intValue();
    }

    /**
     * 实现<code>Number</code>类, 取得长整数值.
     *
     * @return 长整数值
     */
    @Override
    public long longValue() {
        return ((Long) getValue()).longValue();
    }

    /**
     * 实现<code>Number</code>类, 取得<code>double</code>值.
     *
     * @return <code>double</code>值
     */
    @Override
    public double doubleValue() {
        return ((Long) getValue()).doubleValue();
    }

    /**
     * 实现<code>Number</code>类, 取得<code>float</code>值.
     *
     * @return <code>float</code>值
     */
    @Override
    public float floatValue() {
        return ((Long) getValue()).floatValue();
    }

    /**
     * 实现<code>IntegralNumber</code>类, 转换成十六进制整数字符串.
     *
     * @return 十六进制整数字符串
     */
    @Override
    public String toHexString() {
        return Long.toHexString(((Long) getValue()).intValue());
    }

    /**
     * 实现<code>IntegralNumber</code>类, 转换成八进制整数字符串.
     *
     * @return 八进制整数字符串
     */
    @Override
    public String toOctalString() {
        return Long.toOctalString(((Long) getValue()).intValue());
    }

    /**
     * 实现<code>IntegralNumber</code>类, 转换成二进制整数字符串.
     *
     * @return 二进制整数字符串
     */
    @Override
    public String toBinaryString() {
        return Long.toBinaryString(((Long) getValue()).intValue());
    }
}

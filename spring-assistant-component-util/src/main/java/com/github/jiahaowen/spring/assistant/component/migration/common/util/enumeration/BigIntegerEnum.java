package com.github.jiahaowen.spring.assistant.component.migration.common.util.enumeration;


import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 类型安全的枚举类型, 代表一个超长整数.
 *
 * @author chuanmu
 * @since 2017/11/29
 */
public abstract class BigIntegerEnum extends Enum {
    static final long serialVersionUID = 3407019802348379119L;

    /**
     * 创建一个枚举量.
     *
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(long value) {
        return (BigIntegerEnum) createEnum(new BigInteger(String.valueOf(value)));
    }

    /**
     * 创建一个枚举量.
     *
     * @param name 枚举量的名称
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(String name, long value) {
        return (BigIntegerEnum) createEnum(name, new BigInteger(String.valueOf(value)));
    }

    /**
     * 创建一个枚举量.
     *
     * @param name 枚举量的名称
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(String name, String value) {
        return (BigIntegerEnum) createEnum(name, new BigInteger(value));
    }

    /**
     * 创建一个枚举量.
     *
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(BigInteger value) {
        return (BigIntegerEnum) createEnum(value);
    }

    /**
     * 创建一个枚举量.
     *
     * @param name 枚举量的名称
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(String name, BigInteger value) {
        return (BigIntegerEnum) createEnum(name, value);
    }

    /**
     * 创建一个枚举量.
     *
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(BigDecimal value) {
        return (BigIntegerEnum) createEnum(value.toBigInteger());
    }

    /**
     * 创建一个枚举量.
     *
     * @param name 枚举量的名称
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(String name, BigDecimal value) {
        return (BigIntegerEnum) createEnum(name, value.toBigInteger());
    }

    /**
     * 创建一个枚举量.
     *
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(Number value) {
        return (BigIntegerEnum) createEnum(new BigInteger(String.valueOf(value)));
    }

    /**
     * 创建一个枚举量.
     *
     * @param name 枚举量的名称
     * @param value 枚举量的超长整数值
     */
    protected static final BigIntegerEnum create(String name, Number value) {
        return (BigIntegerEnum) createEnum(name, new BigInteger(String.valueOf(value)));
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
                return BigInteger.class;
            }

            @Override
            protected Number getNextValue(Number value, boolean flagMode) {
                if (value == null) {
                    return flagMode ? BigInteger.ONE : BigInteger.ZERO; // 默认起始值
                }

                if (flagMode) {
                    return ((BigInteger) value).shiftLeft(1); // 位模式
                } else {
                    return ((BigInteger) value).add(BigInteger.ONE);
                }
            }

            @Override
            protected boolean isZero(Number value) {
                return ((BigInteger) value).equals(BigInteger.ZERO);
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
        return ((BigInteger) getValue()).intValue();
    }

    /**
     * 实现<code>Number</code>类, 取得长整数值.
     *
     * @return 长整数值
     */
    @Override
    public long longValue() {
        return ((BigInteger) getValue()).longValue();
    }

    /**
     * 实现<code>Number</code>类, 取得<code>double</code>值.
     *
     * @return <code>double</code>值
     */
    @Override
    public double doubleValue() {
        return ((BigInteger) getValue()).doubleValue();
    }

    /**
     * 实现<code>Number</code>类, 取得<code>float</code>值.
     *
     * @return <code>float</code>值
     */
    @Override
    public float floatValue() {
        return ((BigInteger) getValue()).floatValue();
    }

    /**
     * 实现<code>IntegralNumber</code>类, 转换成十六进制整数字符串.
     *
     * @return 十六进制整数字符串
     */
    @Override
    public String toHexString() {
        return ((BigInteger) getValue()).toString(RADIX_HEX);
    }

    /**
     * 实现<code>IntegralNumber</code>类, 转换成八进制整数字符串.
     *
     * @return 八进制整数字符串
     */
    @Override
    public String toOctalString() {
        return ((BigInteger) getValue()).toString(RADIX_OCT);
    }

    /**
     * 实现<code>IntegralNumber</code>类, 转换成二进制整数字符串.
     *
     * @return 二进制整数字符串
     */
    @Override
    public String toBinaryString() {
        return ((BigInteger) getValue()).toString(RADIX_BIN);
    }
}

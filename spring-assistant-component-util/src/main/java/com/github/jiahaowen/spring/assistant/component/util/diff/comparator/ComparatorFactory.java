package com.github.jiahaowen.spring.assistant.component.util.diff.comparator;

import com.github.jiahaowen.spring.assistant.component.util.diff.comparator.impl.ComparableComparator;
import com.github.jiahaowen.spring.assistant.component.util.diff.comparator.impl.StubComparator;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import org.springframework.util.CollectionUtils;

/**
 * 比较器
 *
 * @author jiahaowen.jhw
 * @version $Id: ComparatorFactory.java, v 0.1 2016-10-30 下午9:32 jiahaowen.jhw Exp $
 */
public class ComparatorFactory {

    /** CheckableComparator */
    private final Set<CheckableComparator> checkableComparators = Sets.newHashSet();

    /** Comparable. */
    private final Set<Class<? extends Comparable<?>>> comparables = Sets.newHashSet();

    /** 默认构造器 */
    public ComparatorFactory() {
        init();
        checkableComparators.add(StubComparator.INSTANCE);
    }

    /**
     * 指定比较器
     *
     * <p>CheckableComparator为空时,使用默认比较器
     *
     * @param checkableComparator
     * @param comparable
     */
    public ComparatorFactory(
            Set<CheckableComparator> checkableComparator,
            Set<Class<? extends Comparable<?>>> comparable) {
        init();
        if (!CollectionUtils.isEmpty(comparable)) {
            comparables.addAll(comparable);
        }

        if (CollectionUtils.isEmpty(checkableComparator)
                || CollectionUtils.isEmpty(checkableComparators)) {
            checkableComparators.add(StubComparator.INSTANCE);
        }

        if (!CollectionUtils.isEmpty(checkableComparator)) {
            checkableComparators.addAll(checkableComparator);
        }
    }

    /** 初始化默认支持实现comparable接口的类型 */
    void init() {
        comparables.add(Date.class);
        comparables.add(Timestamp.class);
        comparables.add(java.sql.Date.class);
        comparables.add(Timestamp.class);
        comparables.add(Short.class);
        comparables.add(Long.class);
        comparables.add(Integer.class);
        comparables.add(Double.class);
        comparables.add(Float.class);
        comparables.add(String.class);
        comparables.add(BigDecimal.class);
        comparables.add(BigInteger.class);
        comparables.add(Boolean.class);
        comparables.add(Byte.class);
        comparables.add(ByteBuffer.class);
        comparables.add(Calendar.class);
        comparables.add(Character.class);
        comparables.add(CharBuffer.class);
        comparables.add(Charset.class);
        comparables.add(UUID.class);
    }

    /**
     * 添加自定义comparator
     *
     * @param checkableComparator
     */
    public void addComparator(CheckableComparator checkableComparator) {
        checkableComparators.add(checkableComparator);
    }

    /**
     * 添加自定义comparable
     *
     * @param comparable
     */
    public void addComparable(Class<? extends Comparable<?>> comparable) {
        comparables.add(comparable);
    }

    /** @param checkableComparators */
    public void addComparators(Set<CheckableComparator> checkableComparators) {
        checkableComparators.addAll(checkableComparators);
    }

    /** @param comparables */
    public void addComparables(Set<Class<? extends Comparable<?>>> comparables) {
        comparables.addAll(comparables);
    }

    /**
     * 对指定的对象,找到合适的比较器.
     *
     * @param object
     * @return
     */
    public Optional<Comparator<Object>> findFor(final Object object) {

        if (isComparable(object)) {
            return Optional.<Comparator<Object>>of(ComparableComparator.INSTANCE);
        }

        return findComparatorFor(object);
    }

    /**
     * 对指定的对象,找到合适的比较器
     *
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
    private Optional<Comparator<Object>> findComparatorFor(final Object object) {
        for (final CheckableComparator<?> comparator : checkableComparators) {
            if (comparator.applies(object)) {
                return Optional.of((Comparator<Object>) comparator);
            }
        }
        return Optional.absent();
    }

    /**
     * 判断给定的类型是否实现{@link Comparable}接口,若实现{@link Comparable},则采用默认的比较方法
     *
     * @param object
     * @return
     */
    private boolean isComparable(final Object object) {

        if (comparables.contains(object.getClass())) {
            return true;
        }
        return false;
    }
}

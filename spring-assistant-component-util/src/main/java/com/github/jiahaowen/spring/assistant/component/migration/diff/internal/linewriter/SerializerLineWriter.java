package com.github.jiahaowen.spring.assistant.component.migration.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.migration.common.util.StringUtil;
import com.github.jiahaowen.spring.assistant.component.migration.diff.algorithm.impl.MyersDiff;
import com.github.jiahaowen.spring.assistant.component.migration.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.SerializerFactory;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.serializer.NullSerializer;
import com.github.jiahaowen.spring.assistant.component.migration.diff.internal.serializer.serializer.ToStringSerializer;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;

/**
 * @author jiahaowen.jhw
 * @version $Id: SerializerLineWriter.java, v 0.1 2016-10-30 下午9:39 jiahaowen.jhw Exp $
 */
public class SerializerLineWriter implements CheckableLineWriter {

    private static final MyersDiff myersDiff = new MyersDiff();
    /**
     * 序列化策略工厂
     */
    private final SerializerFactory serializerFactory;

    public SerializerLineWriter(final SerializerFactory serializerFactory) {
        this.serializerFactory = serializerFactory;
    }

    @Override
    public boolean applies(final Object value) {
        return serializerFactory.findFor(value) != null ? true : false;
    }

    @Override
    public List<String> write(final String path, final Object value) {
        final String serializedValue = PathBuilder.extendPathWithValue(path,preHandleObject(value));
        return Collections.singletonList(serializedValue);
    }

    @Override
    public List<CompareResult> write(String path, Object base, Object working) {
        final String serializedBase = PathBuilder.extendPathWithValue(path, preHandleObject(base));
        final String serializedWorking = PathBuilder.extendPathWithValue(path, preHandleObject(working));

        return myersDiff.diffUnsort(Lists.newArrayList(serializedBase),
                Lists.newArrayList(serializedWorking));
    }

    /**
     * 预处理
     *
     * @param object 处理对象
     * @return 处理后对象
     */
    private String preHandleObject(Object object) {

        if (object == null) {
            return NullSerializer.INSTANCE.serialize(object);
        }

        //String类型，empty置为null，否则trim掉空格
        if (object instanceof String) {
            String temp = (String) object;
            return StringUtil.isEmpty(temp.trim()) ? NullSerializer.INSTANCE.serialize(object) : ToStringSerializer
                    .INSTANCE
                    .serialize(temp.trim());
        }

        return ToStringSerializer.INSTANCE.serialize(object);
    }
}

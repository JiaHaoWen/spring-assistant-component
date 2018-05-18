package com.github.jiahaowen.spring.assistant.component.util.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DiffException;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.MissingSerializerException;
import com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer.Serializer;
import com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer.SerializerFactory;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

/**
 * 对实现Map接口的类型进行序列化
 *
 * @author jiahaowen.jhw
 * @version $Id: MapLineWriter.java, v 0.1 2016-10-30 下午9:40 jiahaowen.jhw Exp $
 */
public class MapLineWriter extends TypeSafeCheckableLineWriter<Map<Object, Object>> {

    private final LineWriter lineWriter;

    /** 序列化策略工厂类 */
    private final SerializerFactory serializerFactory;

    /** 构造函数 */
    public MapLineWriter(final LineWriter lineWriter, final SerializerFactory serializerFactory) {

        this.lineWriter = lineWriter;
        this.serializerFactory = serializerFactory;
    }

    /**
     * @param value
     * @return
     */
    @Override
    public boolean applies(final Object value) {
        return value instanceof Map;
    }

    /**
     * @param path 路径描述了给定的对象在对象图中的位置.
     * @param map
     * @return
     * @throws DiffException
     */
    @Override
    public List<String> typeSafeWrite(final String path, final Map<Object, Object> map)
            throws DiffException {
        final List<String> lines = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(map)) {
            for (final Map.Entry<Object, Object> entry : map.entrySet()) {
                final Object key = entry.getKey();
                final Serializer<Object> serializer =
                        findMapKeySerializerOrThrowException(path, key);
                final Object serializedKey = serializer.serialize(key);
                final String extendedPath = PathBuilder.extendPathWithMapIndex(path, serializedKey);
                lines.addAll(lineWriter.write(extendedPath, entry.getValue()));
            }
        }
        return lines;
    }

    /**
     * 根据map中key的类型,寻找对应的序列化策略
     *
     * @param path 路径描述了给定的对象在对象图中的位置.
     * @param key 待寻找序列化策略的key
     * @return
     */
    private Serializer<Object> findMapKeySerializerOrThrowException(
            final String path, final Object key) {
        final Serializer<Object> serializer = serializerFactory.findFor(key);
        if (serializer == null) {
            throw MissingSerializerException.missingMapKeySerializer(path, key.getClass());
        }

        return serializer;
    }

    /**
     * 对象比对
     *
     * @param path
     * @param base
     * @param working @return
     * @throws DiffException
     */
    @Override
    public List<CompareResult> typeSafeWrite(
            final String path, final Map<Object, Object> base, final Map<Object, Object> working)
            throws DiffException {

        List<CompareResult> results = Lists.newArrayList();

        if (base == null && working == null) {
            return results;
        }

        if (working == null || base == null) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("路径:" + path + "下对象类型为base:" + base),
                            Lists.newArrayList("路径:" + path + "下对象类型为working:" + working)));
        }

        if (base.size() == 0 && working.size() == 0) {
            return Lists.newArrayList();
        }

        if (base.size() != working.size()) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("路径:" + path + "下对象的size为" + base.size()),
                            Lists.newArrayList("路径:" + path + "下对象的size为" + working.size())));
        }

        try {
            if (!CollectionUtils.isEmpty(base) && !CollectionUtils.isEmpty(working)) {
                for (final Map.Entry<Object, Object> entry : base.entrySet()) {
                    final Object key = entry.getKey();
                    final Serializer<Object> serializer =
                            findMapKeySerializerOrThrowException(path, key);
                    final Object serializedKey = serializer.serialize(key);
                    final String extendedPath =
                            PathBuilder.extendPathWithMapIndex(path, serializedKey);
                    List<CompareResult> compareResults =
                            lineWriter.write(extendedPath, entry.getValue(), working.get(key));
                    if (!CollectionUtils.isEmpty(compareResults)) {
                        return compareResults;
                    }
                }
            }
        } catch (Throwable t) {
            throw new DiffException("Map属性获取失败,path:" + path);
        }
        return results;
    }
}

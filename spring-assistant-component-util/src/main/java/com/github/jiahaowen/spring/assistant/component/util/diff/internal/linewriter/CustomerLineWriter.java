package com.github.jiahaowen.spring.assistant.component.util.diff.internal.linewriter;

import com.github.jiahaowen.spring.assistant.component.util.diff.differ.CompareResult;
import com.github.jiahaowen.spring.assistant.component.util.diff.exception.DiffException;
import com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer.PathInclusionChecker;
import com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer.PropertyInclusionChecker;
import com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer.SerializerFactory;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * 用户自定义类型
 *
 * @author jiahaowen.jhw
 * @version $Id: CustomerLineWriter.java, v 0.1 2016-11-02 下午8:31 jiahaowen.jhw Exp $
 */
public class CustomerLineWriter implements CheckableLineWriter {

    /** Logger */
    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerLineWriter.class);

    private final LineWriter lineWriter;

    private final SerializerFactory serializerFactory;

    private final PropertyInclusionChecker propertyInclusionChecker;

    private final PathInclusionChecker pathInclusionChecker;

    public CustomerLineWriter(
            final LineWriter lineWriter,
            final SerializerFactory serializerFactory,
            final PropertyInclusionChecker propertyInclusionChecker,
            final PathInclusionChecker pathInclusionChecker) {
        this.lineWriter = lineWriter;
        this.serializerFactory = serializerFactory;
        this.propertyInclusionChecker = propertyInclusionChecker;
        this.pathInclusionChecker = pathInclusionChecker;
    }

    @Override
    public boolean applies(Object object) {

        if (object instanceof Map) {
            return false;
        }
        if (object instanceof Iterable) {
            return false;
        }

        if (object.getClass().isArray()) {
            return false;
        }

        if (serializerFactory.findFor(object) != null) {
            return false;
        }

        return true;
    }

    /**
     * 为自定义复合类型时
     *
     * @param path 路径描述了给定的对象在对象图中的位置.
     * @param value 待序列化的对象
     * @return
     */
    @Override
    public List<String> write(String path, Object value) throws DiffException {

        try {
            List<String> list = Lists.newArrayList();
            Class clazz = value.getClass();
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length > 0) {
                for (Field field : fields) {

                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    if (Modifier.isTransient(field.getModifiers())) {
                        continue;
                    }

                    field.setAccessible(true);

                    // 过滤
                    if (propertyInclusionChecker.apply(field.getName(), clazz.getSimpleName())) {
                        continue;
                    }
                    final String extendedPath =
                            PathBuilder.extendPathWithProperty(path, field.getName());
                    if (pathInclusionChecker.apply(extendedPath)) {
                        continue;
                    }

                    Object object = field.get(value);
                    list.addAll(lineWriter.write(extendedPath, object));
                }
            }
            return list;
        } catch (IllegalAccessException e) {
            throw new DiffException("自定义类的属性获取失败");
        }
    }

    /**
     * 对象比对
     *
     * @param path
     * @param base
     * @param working
     */
    @Override
    public List<CompareResult> write(String path, Object base, Object working)
            throws DiffException {

        if (base == null && working == null) {
            return Lists.newArrayList();
        }

        if (working == null || base == null) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("路径:" + path + "下对象类型为base:" + base),
                            Lists.newArrayList("路径:" + path + "下对象类型为working:" + working)));
        }

        if (!base.getClass().getName().equalsIgnoreCase(working.getClass().getName())) {
            return Lists.newArrayList(
                    new CompareResult(
                            Lists.newArrayList("路径:" + path + "下对象类型为" + base.getClass().getName()),
                            Lists.newArrayList(
                                    "路径:" + path + "下对象类型为" + working.getClass().getName())));
        }

        try {
            Class clazz = base.getClass();
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length > 0) {
                for (Field field : fields) {

                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    if (Modifier.isTransient(field.getModifiers())) {
                        continue;
                    }

                    field.setAccessible(true);

                    // 过滤
                    if (propertyInclusionChecker.apply(field.getName(), clazz.getSimpleName())) {
                        continue;
                    }
                    final String extendedPath =
                            PathBuilder.extendPathWithProperty(path, field.getName());
                    if (pathInclusionChecker.apply(extendedPath)) {
                        continue;
                    }

                    Object baseObject = field.get(base);
                    Object workingObject = field.get(working);

                    List<CompareResult> result =
                            lineWriter.write(extendedPath, baseObject, workingObject);
                    if (!CollectionUtils.isEmpty(result)) {
                        return result;
                    }
                }
            }
            return Lists.newArrayList();
        } catch (IllegalAccessException e) {
            throw new DiffException("自定义类的属性获取失败");
        } catch (Throwable t) {
            throw new DiffException("自定义类的属性获取失败,path:" + path);
        }
    }
}

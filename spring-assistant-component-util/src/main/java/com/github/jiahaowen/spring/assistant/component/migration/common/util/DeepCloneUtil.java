package com.github.jiahaowen.spring.assistant.component.migration.common.util;

import com.google.common.collect.Lists;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chuanmu
 * @since 2018/5/15
 */
public class DeepCloneUtil {
    /** log4j日志，子类可以直接使用 */
    protected static Logger logger = LoggerFactory.getLogger(DeepCloneUtil.class);

    /**
     * 深度克隆
     *
     * @param obj 源对象
     * @return 克隆对象
     */
    public static Object deepClone(Object obj) throws Throwable {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);

        return oi.readObject();
    }

    /**
     * 深度克隆
     *
     * @param objs
     * @return
     * @throws Throwable
     */
    public static Object[] deepClone(Object[] objs) throws Throwable {
        List<Object> objectLists = Lists.newArrayList();
        if (objs.length > 0) {
            for (Object object : objs) {
                objectLists.add(deepClone(object));
            }
        }
        return objectLists.toArray();
    }
}

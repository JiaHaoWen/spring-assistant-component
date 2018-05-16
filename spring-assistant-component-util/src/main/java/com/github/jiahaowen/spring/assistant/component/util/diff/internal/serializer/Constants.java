package com.github.jiahaowen.spring.assistant.component.util.diff.internal.serializer;

import com.google.common.collect.Sets;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * @author jiahaowen.jhw
 * @version $Id: Constants.java, v 0.1 2016-12-08 下午9:42 jiahaowen.jhw Exp $
 */
public class Constants {

    public static final Set<Class<?>> includedTypes = Sets.newHashSet();

    static {
        includedTypes.add(short.class);
        includedTypes.add(long.class);
        includedTypes.add(int.class);
        includedTypes.add(double.class);
        includedTypes.add(float.class);
        includedTypes.add(boolean.class);
        includedTypes.add(byte.class);
        includedTypes.add(Short.class);
        includedTypes.add(Long.class);
        includedTypes.add(Integer.class);
        includedTypes.add(Double.class);
        includedTypes.add(Float.class);
        includedTypes.add(String.class);
        includedTypes.add(BigDecimal.class);
        includedTypes.add(BigInteger.class);
        includedTypes.add(Boolean.class);
        includedTypes.add(Byte.class);
        includedTypes.add(ByteBuffer.class);
        includedTypes.add(Calendar.class);
        includedTypes.add(Character.class);
        includedTypes.add(CharBuffer.class);
        includedTypes.add(Charset.class);
        includedTypes.add(UUID.class);
        includedTypes.add(Date.class);
        includedTypes.add(Timestamp.class);
        includedTypes.add(java.sql.Date.class);
        includedTypes.add(Timestamp.class);
        includedTypes.add(Enum.class);
    }
}

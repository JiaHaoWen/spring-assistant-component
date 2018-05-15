package com.github.jiahaowen.spring.assistant.component.migration.util;

import com.github.jiahaowen.spring.assistant.component.migration.common.util.StringUtil;
import com.github.jiahaowen.spring.assistant.component.migration.util.constans.ArcoreCommonConstants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

/**
 * 日期工具类
 *
 * @author jiahaowen
 * @version $Id: DateUtils.java, v 0.1 2014-11-17 下午6:43:14 jiahaowen Exp $
 */
public class DateUtils {

    /** 完整时间：yyyy-MM-dd HH:mm:ss */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 完整时间（到毫秒） */
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /** 年月日时分秒(无下划线)：yyyyMMddHHmmss */
    public static final String LONG_DATE_FORMAT = "yyyyMMddHHmmss";

    /**
     * 通过格式化字符串获取format对象
     *
     * <p>格式：<code>"yyyy-MM-dd HH:mm"</code>
     *
     * @param format 格式化字符串
     * @return DateFormat format对象
     */
    public static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 将日期对象<code>Date</code>转换为字符串类型输出。
     *
     * <p><code>DateUtils.simpleFormat(new Date()) = "2014-11-17 18:00:00"</code>
     *
     * @param date 日期对象
     * @return String 输出的字符串 格式：<code>yyyy-MM-dd HH:mm:ss</code>
     */
    public static final String simpleFormat(Date date) {
        if (date == null) {
            return StringUtil.EMPTY_STRING;
        }

        return getFormat(DEFAULT_DATE_FORMAT).format(date);
    }

    /**
     * 将日期对象<code>Date</code>转换为字符串类型输出（精确到毫秒）
     *
     * <p><code>DateUtils.simpleFullFormat(new Date()) = "2014-11-17 18:00:00.000"</code>
     *
     * @param date 日期对象
     * @return String 输出的字符串 格式：<code>yyyy-MM-dd HH:mm:ss.SSS</code>
     */
    public static final String simpleFullFormat(Date date) {
        if (date == null) {
            return StringUtil.EMPTY_STRING;
        }

        return getFormat(FULL_DATE_FORMAT).format(date);
    }

    /**
     * 将日期对象<code>Date</code>装换为字符串类型输出
     *
     * <p>
     *  <code>DateUtils.longDateFormat(new Date()) = "20141117180000"
     * </p>
     *
     * @param date   日期对象
     *
     * @return       输出的字符串
     *               格式：<code>yyyyMMddHHmmss</code>
     */
    public static final String longDateFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormat(LONG_DATE_FORMAT).format(date);
    }

    /**
     * 将日期格式字符串转换为日期时间
     *
     * <p><code>DateUtils.string2DateTime("2014-11-17 18:00:00") = Date</code>
     *
     * @param stringDate 时间格式字符串 格式：<code>yyyy-MM-dd HH:mm:ss</code>
     * @return 日期时间 字符串空返回<code>null</code>，异常返回<code>null</code>
     * @throws Exception 如果格式不匹配转换异常，返回空
     */
    public static final Date string2DateTime(String stringDate) {

        if (StringUtil.isBlank(stringDate)) {
            return null;
        }

        try {
            return getFormat(DEFAULT_DATE_FORMAT).parse(stringDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取日期加减(秒)的日期
     *
     * <p><code>DateUtils.getDiffSecondDateTime(2014-11-17 18:00:05,1)  = 2014-11-17 18:00:06</code>
     * <code>DateUtils.getDiffSecondDateTime(2014-11-17 18:00:05,-1) = 2014-11-17 18:00:04</code>
     *
     * @param diffSecond 差异多少秒
     * @return 计算后日期
     */
    public static final Date getDiffSecondDateTime(Date date, int diffSecond) {

        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.SECOND, diffSecond);

        return c.getTime();
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss"格式的字符串转换为日期
     *
     * @param formatString 日期格式字符串
     * @return 日期对象
     */
    public static final Date formatStringToDate(String formatString) {
        if (null == formatString) {
            return null;
        }
        try {
            DateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            return format.parse(formatString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 增加天数
     *
     * @param date 日期
     * @param days 天数
     * @return 日期
     */
    public static final Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 增加秒数
     *
     * @param date 日期
     * @param seconds 秒数
     * @return 日期
     */
    public static final Date addSeconds(Date date, int seconds) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    /**
     * 增加秒数
     *
     * @param date 日期
     * @param seconds 秒数
     * @return 日期
     */
    public static final Date addSeconds(Date date, long seconds) {
        DateTime dTime = new DateTime(date);
        return dTime.plus(seconds * DateTimeConstants.MILLIS_PER_SECOND).toDate();
    }

    /**
     * 减少秒数
     *
     * @param date 日期
     * @param seconds 秒数
     * @return 日期
     */
    public static final Date minusSeconds(Date date, long seconds) {
        DateTime dTime = new DateTime(date);
        return dTime.minus(seconds * DateTimeConstants.MILLIS_PER_SECOND).toDate();
    }

    /**
     * 获取两个时间之间的差（秒）
     *
     * @param minD 较小时间
     * @param MaxD 较大时间
     * @return
     */
    public static final long getSecondsBetween(Date minD, Date MaxD) {
        long diff = MaxD.getTime() - minD.getTime();
        return diff / 1000;
    }

    /**
     * 获取两个时间之间的差（分钟）
     *
     * @param minD 较小时间
     * @param MaxD 较大时间
     * @return
     */
    public static final long getMinutesBetween(Date minD, Date MaxD) {
        long diff = MaxD.getTime() - minD.getTime();
        return diff / (1000 * 60);
    }

    /**
     * 获取两个时间之间的差（微秒）
     *
     * @param minD
     * @param maxD
     * @return
     */
    public static final long getMillSecondsBetween(Date minD, Date maxD) {
        return maxD.getTime() - minD.getTime();
    }

    /**
     * 计算日期偏移量： 计算time1 - time2的差值， 要求time1大于time2
     *
     * @param time1
     * @param time2
     * @return 偏移秒数
     */
    public static long calShiftSeconds(Date time1, Date time2) {

        long shiftSeconds = 0;

        if (time1 == null || time2 == null) {
            return shiftSeconds;
        }

        DateTime dTime1 = new DateTime(time1);
        DateTime dTime2 = new DateTime(time2);
        // 日期比较
        if (dTime1.isAfter(dTime2)) {
            // 计算偏移量
            Duration d = new Duration(dTime2, dTime1);
            shiftSeconds = d.getStandardSeconds();
        }

        return shiftSeconds;
    }

    /**
     * 获取两个时间之间的差（天）
     *
     * @param minD 较小时间
     * @param MaxD 较大时间
     * @return
     */
    public static final int daysBetween(Date minD, Date MaxD) {
        long diffMilSeconds = MaxD.getTime() - minD.getTime();
        long diffDays = diffMilSeconds / (1000 * 60 * 60 * 24);
        return Integer.parseInt(String.valueOf(diffDays));
    }

    /**
     * 获取两个时间之间的差（分钟）
     *
     * @param minD 较小时间
     * @param MaxD 较大时间
     * @return
     */
    public static final long minutesBetween(Date minD, Date MaxD) {
        long diff = MaxD.getTime() - minD.getTime();
        return Long.parseLong(String.valueOf(diff / (1000 * 60)));
    }

    /**
     * 对传入的时间增加偏移量，默认为天
     *
     * @param date
     * @param quantity
     * @param shiftAccuracy
     * @return
     */
    public static final Date addQuantity(Date date, long quantity, String shiftAccuracy) {
        if (date != null) {
            if (StringUtil.equals(shiftAccuracy, ArcoreCommonConstants.SHIFT_SECOND)) {
                return addSeconds(date, quantity);
            } else {
                return addDays(date, (int) quantity);
            }
        } else {
            return null;
        }
    }
}

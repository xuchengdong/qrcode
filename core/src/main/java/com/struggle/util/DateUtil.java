package com.struggle.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xuchengdongxcd@126.com on 2016/12/3.
 */
public final class DateUtil {
    public static final String DEFAULT = "yyyyMMddHHmmss";

    private DateUtil() {
    }

    public static Calendar add(Date date, int field, int amount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar;
    }

    public static String format(Date date, String format) {
        if (format == null) {
            format = DEFAULT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}

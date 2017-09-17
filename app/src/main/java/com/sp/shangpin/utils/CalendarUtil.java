package com.sp.shangpin.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/17/017.
 */

public class CalendarUtil {
    /**
     * 标准的
     */
    public static final String STANDARD = "yyyy-MM-dd HH:mm:ss";

    private Calendar c;

    public CalendarUtil() {
        c = Calendar.getInstance();
    }

    public CalendarUtil(long time, boolean isSecond) {
        this();
        c.setTimeInMillis(isSecond ? time * 1000 : time);
    }

    public CalendarUtil(long time) {
        this();
        c.setTimeInMillis(time);
    }

    public CalendarUtil(int field, int value) {
        c.set(field, value);
    }

    public CalendarUtil(int year, int month, int date) {
        this();
        c.set(year, month, date);
    }

    public CalendarUtil(int year, int month, int date, int hourOfDay, int minute) {
        this();
        c.set(year, month, date, hourOfDay, minute);
    }

    public CalendarUtil(int year, int month, int date, int hourOfDay, int minute, int second) {
        this();
        c.set(year, month, date, hourOfDay, minute, second);
    }

    public int getYear() {
        return c.get(Calendar.YEAR);
    }

    public int getMonth() {
        return c.get(Calendar.MONTH) + 1;
    }

    public int getDate() {
        return c.get(Calendar.DATE);
    }

    public int getHour() {
        return c.get(Calendar.HOUR);
    }

    public int getHourOfDay() {
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return c.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return c.get(Calendar.SECOND);
    }

    public long getTimeInMillis() {
        return c.getTimeInMillis();
    }

    public String format(String format, String yearFlag, String hourFlag) {
        String f = format.replace("-", yearFlag).replace(":", hourFlag);
        return format(f);
    }

    public String format(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(c.getTime());
    }

    public int getMillisecond() {
        return c.get(Calendar.MILLISECOND);
    }

    public int getDateOfMonth() {
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public int getWeekOfWeek() {
        return c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    public int getDayOfWeek() {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return 0;
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            default:
                return 0;
        }
    }

}

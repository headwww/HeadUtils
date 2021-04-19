package com.head.toolkit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 类名称：DateUtil.java <br/>
 * 类描述：时间工具<br/>
 * 创建人：shuwen <br/>
 * 创建时间：2020-08-07 16:48 <br/>
 */
public class DateUtil {

    private static String mYear; // 当前年

    private static String mMonth; // 月

    private static String mDay;

    private static String mWay;

    /**
     * 获取当前日期几月几号
     */
    public static String getMMDDText() {

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        return mMonth + "月" + mDay + "日";
    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String getYYMMDDSymbol() {

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        return mYear + "-" + mMonth + "-" + mDay;
    }

    public static String getYYMMDDText() {

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        return mYear + "年" + mMonth + "月" + mDay + "日";
    }


    public static String getHHMMSymbol() {

        Calendar calendar = Calendar.getInstance();
        // 获取系统的日期
        // 小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 分钟
        int minute = calendar.get(Calendar.MINUTE);

        return hour + ":" + minute;
    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String getYYMMDDHHMMSSSymbol() {

        Calendar calendar = Calendar.getInstance();
        // 获取系统的日期
        // 年
        int year = calendar.get(Calendar.YEAR);
        // 月
        int month = calendar.get(Calendar.MONTH) + 1;
        // 日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 获取系统时间
        // 小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // 分钟
        int minute = calendar.get(Calendar.MINUTE);
        // 秒
        int second = calendar.get(Calendar.SECOND);

        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":"
                + second;
    }

    /**
     * 获取当前是周几
     */
    public static String getWeek() {
        final Calendar c = Calendar.getInstance();
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "周天";
        } else if ("2".equals(mWay)) {
            mWay = "周一";
        } else if ("3".equals(mWay)) {
            mWay = "周二";
        } else if ("4".equals(mWay)) {
            mWay = "周三";
        } else if ("5".equals(mWay)) {
            mWay = "周四";
        } else if ("6".equals(mWay)) {
            mWay = "周五";
        } else if ("7".equals(mWay)) {
            mWay = "周六";
        }
        return mDay;
    }

    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(time));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "周天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "周六";
        }
        return Week;
    }

    /**
     * 比较日期大小
     * <p>
     * 2017年9月7日 16:15:53 xj
     *
     * @param DATE1      第一个时间
     * @param DATE2      第二个时间
     * @param dateFormat 日期格式
     * @return Integer null日期格式有误，1：第一个日期大，0：两个日期一样，-1：第二个日期大
     */
    public static Integer compareDate(String DATE1, String DATE2,
                                      String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        }

        return null;
    }
}
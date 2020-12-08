package com.george.pitch_estimator.utils;

import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    private static SimpleDateFormat sdr;

    public static boolean isEarly(int days, long time) {
        return (currentTimeMillis() - time) > (days * 24 * 3600 * 1000);
    }

    public static int currentTimeSecond() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long[] getTsTimes() {
        long[] times = new long[2];

        Calendar calendar = Calendar.getInstance();

        times[0] = calendar.getTimeInMillis() / 1000;

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        times[1] = calendar.getTimeInMillis() / 1000;

        return times;
    }

    public static String getFormatDatetime(int year, int month, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new GregorianCalendar(year, month, day).getTime());
    }

    public static Date getDateFromFormatString(String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromFormatString(String formatDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromFormatDate(Date date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String convertDate(String date, String fromat) {
        return getStringFromFormatDate(getDateFromFormatString(date, "yyyy-MM-dd HH:mm:ss"), fromat);
    }

    //简历模块的日期格式
    public static String getResumeDate(String date, String fromat) {
        return getStringFromFormatDate(getDateFromFormatString(date, "yyyy-MM-dd HH:mm:ss"), fromat);
    }

    public static String getTiltDate(String date, String fromat) {
        return getStringFromFormatDate(getDateFromFormatString(date, "yyyy/MM/dd HH:mm:ss"), fromat);
    }

    public static String getTrainDate(String date) {
        Date today = new Date(System.currentTimeMillis());
        Date dateFromFormatString = getDateFromFormatString(date, "yyyy-MM-dd HH:mm:ss");
        if (dateFromFormatString != null) {
            if (dateFromFormatString.getYear() == today.getYear()) {
                return getStringFromFormatDate(dateFromFormatString, "MM-dd");
            } else {
                return getStringFromFormatDate(dateFromFormatString, "yyyy-MM-dd");
            }
        } else {
            return "";
        }
    }

    public static String getDiscountDate(String date, String fromat) {
        return getStringFromFormatDate(getDateFromFormatString(date, "yyyy/MM/dd HH:mm:ss"), fromat);
    }

    public static String getPointDate(String date, String fromat) {
        return getStringFromFormatDate(getDateFromFormatString(date, "yyyy.MM.dd HH:mm:ss"), fromat);
    }



    public static String getFormatString(String formatDate) {
        return getFormatString(formatDate, "yyyy-MM-dd");
    }

    public static String getFormatString(String formatDate, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        try {
            return sdf.format(sdf.parse(formatDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNowDatetime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return (formatter.format(new Date()));
    }

    public static int getNow() {
        return (int) ((new Date()).getTime() / 1000);
    }

    public static String getNowDateTime(String format) {
        Date date = new Date();

        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.format(date);
    }

    public static String getDateString(long milliseconds) {
        return getDateTimeString(milliseconds, "yyyyMMdd");
    }

    public static String getTimeString(long milliseconds) {
        return getDateTimeString(milliseconds, "HHmmss");
    }

    public static String getBeijingNowTimeString(String format) {
        TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");

        Date date = new Date(currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        formatter.setTimeZone(timezone);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeZone(timezone);
        String prefix = gregorianCalendar.get(Calendar.AM_PM) == Calendar.AM ? "上午" : "下午";

        return prefix + formatter.format(date);
    }

    public static String getBeijingNowTime(String format) {
        TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");

        Date date = new Date(currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        formatter.setTimeZone(timezone);

        return formatter.format(date);
    }

    public static String getDateTimeString(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }


    public static String getFavoriteCollectTime(long milliseconds) {
        String showDataString = "";
        Date today = new Date();
        Date date = new Date(milliseconds);
        Date firstDateThisYear = new Date(today.getYear(), 0, 0);
        if (!date.before(firstDateThisYear)) {
            SimpleDateFormat dateformatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
            showDataString = dateformatter.format(date);
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            showDataString = dateformatter.format(date);
        }
        return showDataString;
    }

    /**
     * 消息列表的时间显示方式
     *
     * @param date
     * @return
     */
    public static String showMSGTime(String date) {
        String dataString;
        String timeStringBy24;
        Date currentTime;
        if (date.length() > 16) {
            currentTime = getDateFromFormatString(date, "yyyy-MM-dd HH:mm:ss");
        } else {
            currentTime = getDateFromFormatString(date, "yyyy-MM-dd HH:mm");
        }
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = "今天";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }

        SimpleDateFormat timeformatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeStringBy24 = timeformatter24.format(currentTime);

        return dataString + " " + timeStringBy24;
    }

    public static String getTimeShowString(long milliseconds, boolean abbreviate) {
        String dataString;
        String timeStringBy24;

        Date currentTime = new Date(milliseconds);
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = "今天";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else if (!currentTime.before(preyesterday)) {
            dataString = "前天";
        } else if (isSameWeekDates(currentTime, today)) {
            dataString = getWeekOfDate(currentTime);
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }

        SimpleDateFormat timeformatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeStringBy24 = timeformatter24.format(currentTime);

        if (abbreviate) {
            if (!currentTime.before(todaybegin)) {
                return getTodayTimeBucket(currentTime);
            } else {
                return dataString;
            }
        } else {
            return dataString + " " + timeStringBy24;
        }
    }


    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date
     * @return
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨 " + timeformatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "上午 " + timeformatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "下午 " + timeformatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "晚上 " + timeformatter1to12.format(date);
        }
        return "";
    }

    //消息页外层列表时间展示
    public static String getTimeOutString(long milliseconds, boolean abbreviate) {
        String dataString;

        Date currentTime = new Date(milliseconds);
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = "今天 ";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else if (!currentTime.before(preyesterday)) {
            dataString = "前天";
        } else if (isSameWeekDates(currentTime, today)) {
            dataString = getWeekOfDate(currentTime);
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }

        if (abbreviate) {
            if (!currentTime.before(todaybegin)) {
                return dataString + getTodayOutBucket(currentTime);
            } else {
                return dataString;
            }
        } else {
            return dataString;
        }
    }

    //搜索结果列表时间展示
    public static String getTimeOutSearchResultpublic(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "";
        }
        String ftime = "";
        int days = 0;
        try {
            days = getDays(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //log
        if (days == 0) {
            ftime = "今天";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days >= 1 && days <= 2) {
            ftime = "三天内";
        } else if (days > 2 && days <= 7) {
            ftime = "一周内";
        } else if (days > 7 && days <= 14) {
            ftime = "两周内";
        } else if (days > 14 && days <= 30) {
            ftime = "一个月内";
        } else if (days > 30 && days <= 60) {
            ftime = "两个月内";
        } else if (days > 60 && days <= 90) {
            ftime = "三个月内";
        } else if (days > 90) {
            ftime = "三个月前";
        }
        return ftime;
    }

    public static int getDays(String day) throws ParseException {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        int diffDay = 0;
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            diffDay = pre.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
        } else {
            diffDay = (pre.get(Calendar.YEAR) - cal.get(Calendar.YEAR)) * 360 + (pre.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR));
        }
        return diffDay;
    }

    private static ThreadLocal DateLocal = new ThreadLocal();

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return (SimpleDateFormat) DateLocal.get();
    }

    public static int getDiffDays(String day) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        int diffDay = 0;
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
        }
        return diffDay;

    }

    //外层列表时间展示
    public static String getTimeOutStringSS(long milliseconds, boolean abbreviate) {
        String dataString;
        Date currentTime = new Date(milliseconds);
        Calendar calendarYear = Calendar.getInstance();
        int currentYear = calendarYear.get(Calendar.YEAR);
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        if (!currentTime.before(todaybegin)) {
            //今天
            SimpleDateFormat dateformatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        } else if (currentYear - currentTime.getYear() == 1900) {
            //今年
            SimpleDateFormat dateformatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        } else {
            //某年
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }
        if (abbreviate) {
            if (!currentTime.before(todaybegin)) {
                return dataString + getTodayOutBucket(currentTime);
            } else {
                return dataString;
            }
        } else {
            return dataString;
        }
    }

    public static String getTodayOutBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return timeformatter1to12.format(date);
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static boolean isSameDay(long time1, long time2) {
        return isSameDay(new Date(time1), new Date(time2));
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }

    /**
     * 判断两个日期是否在同一周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    public static long getSecondsByMilliseconds(long milliseconds) {
        long seconds = new BigDecimal((float) ((float) milliseconds / (float) 1000)).setScale(0,
                BigDecimal.ROUND_HALF_UP).intValue();
        // if (seconds == 0) {
        // seconds = 1;
        // }
        return seconds;
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else retStr = "" + i;
        return retStr;
    }

    public static String getElapseTimeForShow(long milliseconds) {
        StringBuilder sb = new StringBuilder();
        long seconds = milliseconds / 1000;
        if (seconds < 1)
            seconds = 1;
        long hour = seconds / (60 * 60);
        if (hour != 0) {
            sb.append(hour).append("h");
        }
        long minute = (seconds - 60 * 60 * hour) / 60;
        if (minute != 0) {
            sb.append(minute).append("m");
        }
        long second = (seconds - 60 * 60 * hour - 60 * minute);
        if (second != 0) {
            sb.append(second).append("s");
        }
        return sb.toString();
    }


    public static String getTimestamp(String time) {
        if (time.length() > 10) {
            sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.CHINA);
        } else {
            sdr = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.CHINA);
        }
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
//            String stf = String.valueOf(l);
//            times = stf.substring(0, 10);
            return getTimeOutString(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    //消息页外层列表时间展示
    public static String getTimeOutString(long milliseconds) {
        String dataString;

        Date currentTime = new Date(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = format.format(currentTime) + "(今天)";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = format.format(currentTime) + "(昨天)";
        } else if (!currentTime.before(preyesterday)) {
            dataString = format.format(currentTime) + "(前天)";
        } else {
            return format.format(currentTime);
        }
        return dataString.trim();

    }

    public static String friendlyTimeFormat(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }


        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 0 && days <= 30) {
            ftime = days + "天前";
        } else if (days > 30 && days <= 60) {
            ftime = "1个月前";
        } else if (days > 60 && days <= 90) {
            ftime = "2个月前";
        } else if (days > 90 && days <= 120) {
            ftime = "3个月前";
        } else if (days > 120 && days <= 150) {
            ftime = "4个月前";
        } else if (days > 150 && days <= 180) {
            ftime = "5个月前";
        } else if (days > 180 && days <= 210) {
            ftime = "6个月前";
        } else if (days > 210 && days <= 240) {
            ftime = "7个月前";
        } else if (days > 240 && days <= 270) {
            ftime = "8个月前";
        } else if (days > 270 && days <= 300) {
            ftime = "9个月前";
        } else if (days > 300 && days <= 330) {
            ftime = "10个月前";
        } else if (days > 330 && days <= 360) {
            ftime = "11个月前";
        } else if (days > 360 && days <= 720) {
            ftime = "一年前";
        } else if (days > 1080) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }


    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    public static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };

    public static ThreadLocal<SimpleDateFormat> dateFormater4 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd");
        }
    };


    /**
     * 回收站 黑名单（一天内：HH-mm,一年内 MM-dd,一年外yyyy-MM-dd ）
     *
     * @param sdate
     * @return
     */
    public static String blackListFormatData(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            ftime = dateFormater2.get().format(time);
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days > 360) {
            ftime = dateFormater2.get().format(time);
        } else if (days > 1080) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM/dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> getPastTimeSevenData(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
            fetureDaysList.add(getFetureDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("M/dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDateYMD(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("M/dd");
        String result = format.format(today);
        return result;
    }

    //比较两个时间戳间隔多少天
    public static int differentDaysByMillisecond(long oldTime, long currentTime) {
        int currentDays = (int) (currentTime / 86400000);
        int oldDays = (int) (oldTime / 86400000);
        return currentDays - oldDays;
    }

    public static long getDiffHours(String hrReDate) {
        Date date = toDate(hrReDate);
        long res = date.getTime();
        long current = System.currentTimeMillis();
        return current - res;
    }

    /**
     * @param timestamp
     * @return
     */
    public static String timeAgo(String timestamp, int type) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String nowData = formatter.format(curDate).substring(0, 11);
        String deliveryDate = timestamp.substring(0, 11);

        if (nowData.equals(deliveryDate))
            if (type == 0) {
                return "今天";
            } else {
                return "今天" + timestamp.substring(11, 16);
            }
        else if (type == 0) {
            return timestamp.substring(5, 11);
        } else {
            return timestamp.substring(5, 16);
        }

    }

    /**
     * @param timestamp
     * @return 消息页企业对话日期显示使用
     */
    public static String timeAgo2(String timestamp, int type) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();
        String yesterday = formatter.format(d).substring(0, 11);

        Date curDate = new Date(System.currentTimeMillis());
        String nowData = formatter.format(curDate).substring(0, 11);
        String deliveryDate = timestamp.substring(0, 11);

        if (nowData.equals(deliveryDate))
            return "今天" + timestamp.substring(11, 16);
        if (yesterday.equals(deliveryDate))
            return "昨天" + timestamp.substring(11, 16);
        else
            return timestamp.substring(0, 16);

    }

    /**
     * 七天内。七天前
     *
     * @param timestamp
     * @return
     */
    public static String timeAgo1(long timestamp) {
        if ((timestamp + "").length() == 13)
            timestamp = timestamp / 1000l;
        int days = stampDays(timestamp, System.currentTimeMillis() / 1000L);
//        时间格式： 若为今天，则‘今天’；
//        若为一周内，则显示 ‘n天前’；
//        若为一周外，则显示具体日期 ‘11月15日’
        long day = 1;
        if (days < day)
            return "今天";
        else if (days >= day && days < day * 2)
            return "昨天";
        else if (days >= day * 2 && days < 3)
            return "前天";
        else if (days >= day * 3 && days < day * 7)
//            return stampToMonth(timestamp);
//        else if (days >= day * 365)
            return "七天内";
        else
            return "七天前";
    }

    /**
     * 今天 和 日期
     *
     * @return
     */
    public static String getJobDate(String date) {
        String dataString;
        Date currentTime;
        if (date.length() > 16) {
            currentTime = getDateFromFormatString(date, "yyyy-MM-dd HH:mm:ss");
        } else {
            currentTime = getDateFromFormatString(date, "yyyy-MM-dd HH:mm");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd", Locale.getDefault());
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);

        Calendar calendarYear = Calendar.getInstance();
        int currentYear = calendarYear.get(Calendar.YEAR);
        if (!currentTime.before(todaybegin)) {
            dataString = "今天";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else if (!currentTime.before(preyesterday)) {
            dataString = "前天";
        } else if (currentYear - currentTime.getYear() == 1900) {
            return format1.format(currentTime);
        } else {
            return format.format(currentTime);
        }
        return dataString;
    }

    /**
     * s1时间小于s2时间
     *
     * @param s1
     * @param s2
     * @return
     */
    public static int stampDays(long s1, long s2) {
        int days = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(s1 * 1000);
        Date date2 = new Date(s2 * 1000);
        String date1_1 = simpleDateFormat.format(date1);
        String date2_2 = simpleDateFormat.format(date2);
        try {
            date1 = simpleDateFormat.parse(date1_1);
            date2 = simpleDateFormat.parse(date2_2);
            days = (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 比较当前时间和服务器返回时间大小
     *
     * @param nowDate
     * @param compareDate
     * @return
     */
    public static boolean compareDate(String nowDate, String compareDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date now = df.parse(nowDate);
            Date compare = df.parse(compareDate);
            if (now.before(compare)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void aaaaa(String startDate) {
        long curTime = System.currentTimeMillis();
        Date date = getDateFromFormatString(startDate);
        long startTime = date.getTime();
        long suplesTime = startTime - curTime;
        if (suplesTime <= 0) {
            return;
        }

    }

    public static String getSuplesTime(String fromDate, String toDate, boolean showDay, boolean showHour, boolean isCallBaclAll) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //前的时间
            Date fd = df.parse(fromDate);
            //后的时间
            Date td = df.parse(toDate);
            //两时间差,精确到毫秒
            long diff = td.getTime() - fd.getTime();
            if (diff < 0) {
                return "";
            }
            long day = diff / 86400000;                         //以天数为单位取整
            long hour = diff % 86400000 / 3600000;               //以小时为单位取整
            long min = diff % 86400000 % 3600000 / 60000;       //以分钟为单位取整
            long seconds = diff % 86400000 % 3600000 % 60000 / 1000;   //以秒为单位取整
            if (isCallBaclAll) {
                return day + "-" + hour + "-" + min + "-" + seconds;
            }
            //天时分秒
            if (showDay) {
                return day + "天" + hour + "小时" + min + "分" + seconds + "秒";
            }
            if (showHour) {
                return hour + "小时" + min + "分" + seconds + "秒";
            }
            return min + "分" + seconds + "秒";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMyCourseTime(long timeSec) {

        try {

            long diff = timeSec;
            if (diff < 0) {
                return "";
            }
            //以天数为单位取整
            long hour = diff / 3600;

            long min;       //以分钟为单位取整
            if (diff > 3600) {
                if (diff%60>0) {
                    min = (diff % 3600 / 60)+1;
                }else {
                    min = (diff % 3600 / 60);
                }
            } else {
                if (diff%60>0) {
                    min = (diff / 60)+1;
                }else {
                    min = (diff / 60);
                }
            }
            if (min != 0 && hour != 0) {
                return +hour + "时" + min + "分";
            } else if (hour != 0) {
                return +hour + "时";
            } else {
                return +min + "分";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0分";
    }

    public static String getUntilTime(long timeSeconds) {

        try {


            long diff = timeSeconds;
            if (diff < 0) {
                return "";
            }
            long day = diff / 86400;                         //以天数为单位取整
            long hour = diff % 86400 / 3600;               //以小时为单位取整
            long min = diff % 86400 % 3600 / 60;       //以分钟为单位取整


            return "还剩" + day + "天" + hour + "时" + min + "分";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "还剩";
    }


    /**
     * 获取网络时间
     *
     * @return
     */
    private Long getNetTime() {
        URL url = null;//取得资源对象
        try {
            url = new URL("http://www.baidu.com");
            //url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            //url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
//            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String f = formatter.format(calendar.getTime());
            return calendar.getTime().getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}

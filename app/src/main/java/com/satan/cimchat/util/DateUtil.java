package com.satan.cimchat.util;

import android.util.Log;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private final static String TAG = DateUtil.class.getSimpleName();
    public final static SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat DATETIME_WITH_MILLISECS_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public final static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");
    public final static SimpleDateFormat DAY_FORMATTER = new SimpleDateFormat("MM月dd日");
    public final static SimpleDateFormat DAY_FORMATTER_SHORT = new SimpleDateFormat("M月d日");
    public final static SimpleDateFormat DATETIME_WITH_ZONE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public final static SimpleDateFormat TWITTER_FORMATTER = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
    public final static SimpleDateFormat DAY_WITH_PERIOD = new SimpleDateFormat("M月d日 a");
    public final static SimpleDateFormat DAY_WITH_HOUR = new SimpleDateFormat("MM-dd HH:mm");
    private final static SimpleDateFormat HOUR_WITH_MINUTE = new SimpleDateFormat("HH:mm");
    private static final int GOODS = 0x02;
    private static final int MOUTH = 0x03;
    private static final int DAY = 0x04;
    private static final int NOON = 0x05;
    private static final int RESERVED = 0x06;

    /**
     * Format date time to string "yyyy-MM-dd HH:mm:ss".
     *
     * @param dateTime
     * @return date time string
     */
    public static String formatDateTime(Date dateTime) {
        return DATETIME_FORMATTER.format(dateTime);
    }

    /**
     * Format million seconds to string "yyyy-MM-dd HH:mm:ss".
     *
     * @param millionSeconds
     * @return date time string
     */
    public static String formatDateTime(long millionSeconds) {
        return formatDateTime(new Date(millionSeconds));
    }

    /**
     * Format date time to string "yyyy-MM-dd HH:mm:ss.SSS".
     *
     * @param dateTime
     * @return date time string
     */
    public static String formatDateTimeWithMilliSecs(Date dateTime) {
        return DATETIME_WITH_MILLISECS_FORMATTER.format(dateTime);
    }

    /**
     * Format date to string "yyyy-MM-dd".
     *
     * @param date
     * @return date string
     */
    public static String formatDate(Date date) {
        return DATE_FORMATTER.format(date);
    }

    public static String formatDay(Date date) {
        return DAY_FORMATTER.format(date);
    }

    public static String formatDaySimple(Date date) {
        return DAY_FORMATTER_SHORT.format(date);
    }

    public static String formatDaywithHour(Date date) {
        return DAY_WITH_HOUR.format(date);
    }

    public static String formatDayPeriod(Date date) {
        return DAY_WITH_PERIOD.format(date);
    }

    public static String formatHourWithMinute(Date date) {
        return HOUR_WITH_MINUTE.format(date);
    }

    /**
     * Format time to string "HH:mm:ss".
     *
     * @param dateTime
     * @return time string
     */
    public static String formatTime(Date dateTime) {
        return TIME_FORMATTER.format(dateTime);
    }

    /**
     * Format time to string "MMM dd yyyy".
     *
     * @param dateTime
     * @return time string
     */
    public static String formatTwitterDate(Date dateTime) {
        return TWITTER_FORMATTER.format(dateTime);
    }

    /**
     * Parse date string to Date "yyyy-MM-dd HH:mm:ss".
     *
     * @param dateTimeStr
     * @return Date
     */
    public static Date parseDateTime(String dateTimeStr) {
        try {
            return DATETIME_FORMATTER.parse(dateTimeStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse date string to Date "yyyy-MM-dd".
     *
     * @param dateStr
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        try {
            return DATE_FORMATTER.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse time string to Time "HH:mm:ss".
     *
     * @param timeStr
     * @return Date
     */
    public static Time parseTime(String timeStr) {
        try {
            return new Time(TIME_FORMATTER.parse(timeStr).getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse date string to Date "yyyy-MM-ddTHH:mm:ssZ".
     *
     * @param dateTimeStr
     * @return Date
     */
    public static Date parseDateTimeWithZone(String dateTimeStr) {
        try {
            return DATETIME_WITH_ZONE_FORMATTER.parse(dateTimeStr);
        } catch (Exception e) {
            return null;
        }
    }


    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
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
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param date
     * @return boolean
     */
    public static boolean isToday(String date) {
        if (StringUtil.isEmpty(date)) return false;
        boolean b = false;
        Date time = toDate(date);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            Log.e("TAG", "nowDate:" + nowDate + "  timeDate:" + timeDate);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    public static String toTimePeriod(String sdate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDateTime(sdate));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (0 <= hour && hour < 12) {
            return "上午";
        } else if (12 <= hour && hour < 18) {
            return "下午";
        } else {
            return "晚上";
        }
    }


    public static String[] selectDate(int type, int year, int mouth) {
        String[] itemStrings = null;
        StringBuilder sb;
        int itemLength = 0;
        switch (type) {
            case MOUTH:
                itemStrings = new String[]{"本月", "下月"};
                break;
            case DAY:
                if (mouth == 1 || mouth == 3 || mouth == 5 || mouth == 7 || mouth == 8 || mouth == 10 || mouth == 12) {
                    itemLength = 31;
                } else if (mouth == 2) {
                    if (isLeapYear(year)) {
                        itemLength = 29;
                    } else {
                        itemLength = 28;
                    }
                } else if (mouth == 4 || mouth == 6 || mouth == 9 || mouth == 11) {
                    itemLength = 30;
                }
                itemStrings = new String[itemLength];
                for (int i = 0; i < itemStrings.length; i++) {
                    sb = new StringBuilder();
                    sb.append(i + 1);
                    sb.append("日");
                    itemStrings[i] = sb.toString();
                }
                break;
            case NOON:
                itemStrings = new String[]{"上午", "下午", "晚上"};
                break;
            case RESERVED:
                itemStrings = new String[7];
                for (int i = 0; i < itemStrings.length; i++) {
                    sb = new StringBuilder();
                    sb.append(i + 1);
                    sb.append("天");
                    itemStrings[i] = sb.toString();
                }
                break;
        }
        return itemStrings;
    }

    /**
     * 判断是否是闰年
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }


    /**
     * 比较两个日期之间的大小
     *
     * @param date1
     * @param date2
     * @return 前者大于后者返回true 反之false
     */
    public static boolean compareDate(String date1, String date2) {
        Log.e(TAG, date1 + "----" + date2);

        try {
            Date d1 = dateFormater2.get().parse(date1);
            Date d2 = dateFormater2.get().parse(date2);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            int result = c1.compareTo(c2);
            if (result >= 0)
                return true;
            else
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

}

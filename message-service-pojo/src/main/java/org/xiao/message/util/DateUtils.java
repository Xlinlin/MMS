package org.xiao.message.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtils
{

    /**
     * 年-月-日 时:分:秒 显示格式
     */
    // 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
    public static final String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyyMMddHHmm";
    /**
     * 年-月-日 显示格式
     */
    public static final String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    /**
     * Date类型转为指定格式的String类型
     *
     * @param source
     * @param pattern
     * @return
     */
    public static String DateToString(Date source, String pattern)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }

    public static String timeToString(Date source)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        return simpleDateFormat.format(source);
    }

    public static String currentTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        return simpleDateFormat.format(new Date());

    }
    public static String currentTimeMinute()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MINUTE_PATTERN);
        return simpleDateFormat.format(new Date());

    }

    /**
     * unix时间戳转为指定格式的String类型
     * <p>
     * <p>
     * System.currentTimeMillis()获得的是是从1970年1月1日开始所经过的毫秒数
     * unix时间戳:是从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数,不考虑闰秒
     *
     * @param source
     * @param pattern
     * @return
     */
    public static String timeStampToString(long source, String pattern)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(source * 1000);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期转换为时间戳(unix时间戳,单位秒)
     *
     * @param date
     * @return
     */
    public static long dateToTimeStamp(Date date)
    {
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.getTime() / 1000;

    }

    /**
     * 字符串转换为对应日期(可能会报错异常)
     *
     * @param source
     * @param pattern
     * @return
     */
    public static Date stringToDate(String source, String pattern)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try
        {
            date = simpleDateFormat.parse(source);
        }
        catch (ParseException e)
        {
            log.error("字符串转换日期异常", e);
        }
        return date;
    }

    /**
     * 获得当前时间对应的指定格式
     *
     * @param pattern
     * @return
     */
    public static String currentFormatDate(String pattern)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());

    }

    /**
     * 获得当前unix时间戳(单位秒)
     *
     * @return 当前unix时间戳
     */
    public static long currentTimeStamp()
    {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * [简要描述]:获取当前日期前天<br/>
     * [详细描述]:<br/>
     *
     * @return java.lang.String
     * jun.liu  2018/11/13 - 10:56
     **/
    public static String getDateBefore(String pattern, int day)
    {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DATE, day);
        Date lastDay = ca.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(lastDay);
    }

    /**
     * [简要描述]:获取某一天的前一天<br/>
     * [详细描述]:<br/>
     *
     * @param date    :
     * @param pattern :
     * @param day     :
     * @return java.lang.String
     * jun.liu  2018/11/14 - 17:01
     **/
    public static String getDateBefore(Date date, String pattern, int day)
    {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, day);
        Date lastDay = ca.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(lastDay);
    }

    /**
     * [简要描述]:获取本周第一天<br/>
     * [详细描述]:<br/>
     *
     * @return java.lang.String
     * jun.liu  2018/11/13 - 16:21
     **/
    public static String getWeekFirstDay(Date date, String pattern)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1)
        {
            d = 0;
        }
        else
        {
            d = 1 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        return new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN).format(cal.getTime());
    }

    /**
     * [简要描述]:获取本周最后一天<br/>
     * [详细描述]:<br/>
     *
     * @param pattern :
     * @return java.lang.String
     * jun.liu  2018/11/13 - 16:30
     **/
    public static String getWeekLastDay(Date date, String pattern)
    {
        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1)
        {
            d = 0;
        }
        else
        {
            d = 1 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN).format(cal.getTime());
    }
}
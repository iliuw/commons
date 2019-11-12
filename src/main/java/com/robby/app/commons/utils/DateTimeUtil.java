package com.robby.app.commons.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 日期时间处理函数库
 * Created @ 2019/11/12
 * @author liuwei
 */
public class DateTimeUtil {

    /**
     * 获得当前时间
     * @return LocalDateTime
     */
    public static LocalDateTime getNow() {
        return LocalDateTime.now(ZoneId.of("GMT+8"));
    }

    /**
     * 获得当前时间的Date对象
     * @return Date
     */
    public static Date getDate() {
        return Date.from(getNow().atZone(ZoneId.of("GMT+8")).toInstant());
    }

    /**
     * 格式化时间，输出String
     * @param date
     * @param patten
     * @return
     */
    public static String format(LocalDateTime date, String patten) {
        return DateTimeFormatter.ofPattern(patten).format(date);
    }

    /**
     * 格式化时间，输出LocalDateTime
     * @param date
     * @param patten
     * @return
     */
    public static LocalDateTime parse(String date, String patten) {
        return LocalDateTime.from(DateTimeFormatter.ofPattern(patten).parse(date));
    }

    /**
     * 获取一个当前时间之前的LocalDateTime对象
     * @param exp
     * @param tu
     * @return
     */
    public static LocalDateTime getExpireDate(int exp, TimeUnit tu) {
        Date now = Date.from(getNow().atZone(ZoneId.of("GMT+8")).toInstant());
        long mulit = exp;
        double r = 1000.00;
        switch (tu) {
            case DAYS:
                mulit *= 24 * 3600;
                break;
            case MINUTES:
                mulit *= 60;
                break;
            case HOURS:
                mulit *= 3600;
                break;
            case MILLISECONDS:
                r = 1;
                break;
            case MICROSECONDS:
                r = 0.001;
                break;
        }
        mulit *= r;
        Date des = new Date(now.getTime() - mulit);
        LocalDateTime ds = LocalDateTime.ofInstant(des.toInstant(), ZoneId.of("GMT+8"));

        return LocalDateTime.of(ds.getYear(), ds.getMonth(), ds.getDayOfMonth(), ds.getHour(), ds.getMinute(), ds.getSecond());
    }

}

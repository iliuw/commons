package com.robby.app.commons.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时钟工厂
 * Created @ 2020/2/19
 * @author liuwei
 */
public class CalenderFactory {

    /**
     * 获取当前时间的LocalDateTime对象
     * @return
     */
    public static LocalDateTime getNow() {
        return LocalDateTime.now(ZoneId.of("GMT+8"));
    }

    public static Date getNowV2() {
        return fromLocal(getNow());
    }

    /**
     * 获取当前时间的Date对象
     * @return
     */
    public static Date getNowToFront() {
        LocalDateTime now = getNow();
        return fromLocal(now);
    }

    /**
     * LocalDateTime from Date
     * @param date
     * @return
     */
    public static LocalDateTime toLocal(Date date) {
        return date.toInstant().atZone(ZoneId.of("GMT+8")).toLocalDateTime();
    }

    /**
     * Date from LocalDateTime
     * @param date
     * @return
     */
    public static Date fromLocal(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.of("GMT+8")).toInstant());
    }

    /**
     * LocalDateTime to String
     * @param date
     * @param pattern
     * @return
     */
    public static String getShow(LocalDateTime date, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(date);
    }

    /**
     * Date to String
     * @param date
     * @param pattern
     * @return
     */
    public static String getShow(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * String to LocalDateTime
     * @param date
     * @param pattern
     * @return
     */
    public static LocalDateTime parse(String date, String pattern) {
        return LocalDateTime.from(DateTimeFormatter.ofPattern(pattern).parse(date));
    }

}

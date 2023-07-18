package com.wbh.testsecurity.chat.function;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Time {
    public static Timestamp getNow() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }
    public static LocalDateTime stringToTimestamp(String time) {
        String timestampString = time.replaceAll("\\s+$", "");
        String format = "yyyy-MM-dd HH:mm:ss.SSS";
        if (timestampString.length() == 21) {
            format = "yyyy-MM-dd HH:mm:ss.S";
        } else if (timestampString.length() == 22) {
            format = "yyyy-MM-dd HH:mm:ss.SS";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(timestampString, formatter);
    }

    public static Timestamp getOrigin() {
        LocalDateTime origin = LocalDateTime.of(2000, 1, 1, 0, 0, 000);
        return Timestamp.valueOf(origin);
    }
    public static boolean comTime(String timestampString, int timedif) {
        if (timestampString.equals("2000-01-01 00:00:00.0")){
            return true;
        }
//        System.out.println(timestampString);
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 解析时间戳字符串为 LocalDateTime 对象
        LocalDateTime timestampDateTime = stringToTimestamp(timestampString);

        // 计算时间差值
        long minutesDifference = ChronoUnit.MINUTES.between(timestampDateTime, currentDateTime);

        // 判断时间差值是否大于 5 分钟
        return minutesDifference > timedif;
    }
    public static Timestamp getDelete(Integer amount) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysAgo = now.minus(amount, ChronoUnit.DAYS);
        return Timestamp.valueOf(thirtyDaysAgo);
    }
}

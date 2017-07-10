package web.log.monitor.common.time;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/5 *** fulongwen *** Initial
 ***********************************************************/
public class TimeUtil {


    public static String  getStandardTimeMinuteText(LocalDateTime time){

        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter.format(time);
    }

    public static Long getMinuteId(LocalDateTime time){

        BigDecimal year = BigDecimal.valueOf(time.getYear());
        BigDecimal month = BigDecimal.valueOf(time.getMonthValue());
        BigDecimal day = BigDecimal.valueOf(time.getDayOfMonth());
        BigDecimal hour = BigDecimal.valueOf(time.getHour());
        BigDecimal minute = BigDecimal.valueOf(time.getMinute());

        BigDecimal one  = year.multiply(BigDecimal.valueOf(100000000));
        BigDecimal two = month.multiply(BigDecimal.valueOf(1000000));
        BigDecimal three = day.multiply(BigDecimal.valueOf(10000));
        BigDecimal four = hour.multiply(BigDecimal.valueOf(100));

        return one.add(two).add(three).add(four).add(minute).longValue();
    }


    public static void main(String[] arg){
        LocalDateTime now = LocalDateTime.now();
        long minuteId = TimeUtil.getMinuteId(now);
        System.out.println(minuteId);
        System.out.println(now);
        System.out.println(TimeUtil.getStandardTimeMinuteText(now));

    }
}

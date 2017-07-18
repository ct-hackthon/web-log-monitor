package web.log.monitor.common.time;

import java.math.BigDecimal;
import java.text.Bidi;
import java.time.*;
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

        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(time);
    }

    public static Long getTimeidFromString(String time){
        //"04 01 05:00:08"
        int year = LocalDateTime.now().getYear();
        String[] timeStr = time.split(" ");
        int month = Integer.valueOf(timeStr[0]);
        int day = Integer.valueOf(timeStr[1]);
        LocalTime localTime = LocalTime.parse(timeStr[2]);
        LocalDate localDate = LocalDate.of(year,month,day);
        LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
        return getMinuteId(localDateTime);
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

    public static Long getMinuteId(Long epoySeconds){
        Instant instant = Instant.ofEpochMilli(epoySeconds);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return getMinuteId(localDateTime);
    }

    public static Long getMiniteId(String time){
        //2017-04-01 13:00:00.385
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime =  LocalDateTime.parse(time,formatter);
        return getMinuteId(localDateTime);
    }

    public static String getMinuteText(Long timeId){
        BigDecimal timeIdBd = BigDecimal.valueOf(timeId);

        // 计算年
        BigDecimal yearBd = timeIdBd.divide(BigDecimal.valueOf(100000000));
        int year = yearBd.intValue();

        // 计算月
        BigDecimal base = timeIdBd.subtract(BigDecimal.valueOf(year).multiply(BigDecimal.valueOf(100000000)));  //YYDDhhmm
        BigDecimal  monthBd = base.divide(BigDecimal.valueOf(1000000));
        int month = monthBd.intValue();

        // 计算日
        base = base.subtract(BigDecimal.valueOf(month).multiply(BigDecimal.valueOf(1000000)));  //DDhhmm
        BigDecimal dayBd = base.divide(BigDecimal.valueOf(10000));
        int day = dayBd.intValue();


        // 计算小时
        base = base.subtract(BigDecimal.valueOf(day).multiply(BigDecimal.valueOf(10000)));  //hhmm
        BigDecimal hourBd = base.divide(BigDecimal.valueOf(100));
        int hour = hourBd.intValue();

        // 计算分钟
        BigDecimal minuteBd = base.subtract(BigDecimal.valueOf(hour).multiply(BigDecimal.valueOf(100)));  //mm
        int minute = minuteBd.intValue();

        LocalDateTime ld =  LocalDateTime.of(year,month,day,hour,minute);
        return getStandardTimeMinuteText(ld);
    }

    public static Long raiseTimeId ( Long timeId){
        BigDecimal timeIdBd = BigDecimal.valueOf(timeId);

        // 计算年
        BigDecimal yearBd = timeIdBd.divide(BigDecimal.valueOf(100000000));
        int year = yearBd.intValue();

        // 计算月
        BigDecimal base = timeIdBd.subtract(BigDecimal.valueOf(year).multiply(BigDecimal.valueOf(100000000)));  //YYDDhhmm
        BigDecimal  monthBd = base.divide(BigDecimal.valueOf(1000000));
        int month = monthBd.intValue();

        // 计算日
        base = base.subtract(BigDecimal.valueOf(month).multiply(BigDecimal.valueOf(1000000)));  //DDhhmm
        BigDecimal dayBd = base.divide(BigDecimal.valueOf(10000));
        int day = dayBd.intValue();


        // 计算小时
        base = base.subtract(BigDecimal.valueOf(day).multiply(BigDecimal.valueOf(10000)));  //hhmm
        BigDecimal hourBd = base.divide(BigDecimal.valueOf(100));
        int hour = hourBd.intValue();

        // 计算分钟
        BigDecimal minuteBd = base.subtract(BigDecimal.valueOf(hour).multiply(BigDecimal.valueOf(100)));  //mm
        int minute = minuteBd.intValue();

        LocalDateTime ld =  LocalDateTime.of(year,month,day,hour,minute).plusMinutes(1);
        return getMinuteId(ld);

    }

    public static void main(String[] arg){

//        Long timeId =  201704011212L;
//        System.out.println(getTimeidFromString("04 01 12:12:00"));

        String s  ="118.754033,32.102910$200$http://mloganalysts.corp.qunar.com/api/log/ueLog$201$46011.0$87$wifi$8B599317-96F3-4198-8215-84DDD95754C8$10010$$2017-04-01 00:17:53.770$118.754033$32.10291$http://mloganalysts.corp.qunar.com$mloganalysts.corp.qunar.com$17";
        String[] ss = s.split("\\$");
        System.out.println(ss.length);



    }
}

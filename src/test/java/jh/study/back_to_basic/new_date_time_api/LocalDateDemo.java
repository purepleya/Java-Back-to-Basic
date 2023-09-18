package jh.study.back_to_basic.new_date_time_api;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

public class LocalDateDemo {

    @Test
    void constructionDemo() {
        System.setProperty("user.timezone", "UTC");

        Map<String, LocalDate> localDates = new HashMap<>();
        localDates.put("min", LocalDate.MIN);
        localDates.put("max", LocalDate.MAX);
        localDates.put("epoch", LocalDate.EPOCH);
        localDates.put("now", LocalDate.now());
        localDates.put("nowByZoneId", LocalDate.now(ZoneId.of("Asia/Seoul")));
        localDates.put("nowByZoneId2", LocalDate.now(ZoneId.of("America/New_York")));


        long yesterdayOffset = (LocalDateTime.now().getHour() + 1) * -1;
        localDates.put("nowByClock(offset:" + yesterdayOffset + ")", LocalDate.now(Clock.offset(Clock.systemUTC(), Duration.of(yesterdayOffset, ChronoUnit.HOURS))));

        localDates.put("of", LocalDate.of(2020, 1, 1));
        localDates.put("ofWithMonthEnum", LocalDate.of(2020, Month.FEBRUARY, 1));
        localDates.put("ofYearDay", LocalDate.ofYearDay(2020, 1));
        localDates.put("ofEpochDay", LocalDate.ofEpochDay(1));
        localDates.put("parse", LocalDate.parse("2020-01-01"));
        localDates.put("parseWithFormatter", LocalDate.parse("2020.01.01", DateTimeFormatter.ofPattern("yyyy.MM.dd")));

        localDates.forEach((k, v) -> {
            System.out.printf("%s %s : %s%n", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), k, v.format(DateTimeFormatter.ISO_LOCAL_DATE));
        });
    }


    @Test
    void getDemo() {
        LocalDate now = LocalDate.now();

        // get(TemporalField field)	- 요청한 field 값을 int 형으로 반환.
        System.out.println(now.get(ChronoField.DAY_OF_MONTH));

        // getLong(TemporalField field)	- 요청한 field 값을 long 형으로 반환.
        System.out.println(now.getLong(ChronoField.MONTH_OF_YEAR));

        // 기원전(BC)인지 후(AD)인지 IsoEra 형으로 반환 한다. BCC(기원전) / CE(기원후)
        System.out.println(now.getEra());
        System.out.println(LocalDate.MIN.getEra());

        // day of month(일)를 int 형으로 반환 한다. get(ChronoField.DAY_OF_MONTH) 와 동일.
        System.out.println(now.getDayOfMonth());

        // 요일을 DayOfWeek 형으로 반환.
        System.out.println(now.getDayOfWeek());

        // 일년중 몇번째 날 인지 int 형으로 반환 한다. get(ChronoField.DAY_OF_YEAR) 와 동일.
        System.out.println(now.getDayOfYear());

        // 년도를 int 형으로 반환 한다. get(ChronoField.YEAR) 와 동일.
        System.out.println(now.getYear());

        // 몇월인 지 Month 형으로 반환.
        System.out.println(now.getMonth());

        // 몇 월인지 int 형으로 반환 한다. get(ChronoField.MONTH_OF_YEAR) 와 동일.
        System.out.println(now.getMonthValue());
    }

}

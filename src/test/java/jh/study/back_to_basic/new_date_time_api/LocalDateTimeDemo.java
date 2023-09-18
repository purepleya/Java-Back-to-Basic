package jh.study.back_to_basic.new_date_time_api;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

public class LocalDateTimeDemo {

    @Test
    void constructionDemo() {
//        now()
//        now(ZoneId zone)
//        of(int year, Month month, int dayOfMonth, int hour, int minute)
//        of(int year, Month month, int dayOfMonth, int hour, int minute, int second)
//        of(int year, Month month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond)
//        of(int year, int month, int dayOfMonth, int hour, int minute)
//        of(int year, int month, int dayOfMonth, int hour, int minute, int second)
//        of(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond)
//        of(LocalDate date, LocalTime time)

//        ofEpochSecond(long epochSecond, int nanoOfSecond, ZoneOffset offset)
//        parse(CharSequence text)
//        parse(CharSequence text, DateTimeFormatter formatter)
    }

    @Test
    void getDemo() {
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(localDateTime);

//        minus
//        minus(TemporalAmount amount)
        System.out.println(localDateTime.minus(Duration.ofDays(3)));

//        minus(long amountToSubtract, TemporalUnit unit)
        System.out.println(localDateTime.minus(5, ChronoUnit.DAYS));

        System.out.println(localDateTime.minusDays(2));
        System.out.println(localDateTime.minusHours(2));


//        plus
        System.out.println(localDateTime.plus(Duration.ofDays(2)));
        System.out.println(localDateTime.plus(3, ChronoUnit.DAYS));
        System.out.println(localDateTime.plusDays(4));
        System.out.println(localDateTime.plusHours(5));

//        with
        System.out.println(localDateTime.with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println(localDateTime.with(ChronoField.HOUR_OF_DAY, 10));
        System.out.println(localDateTime.withYear(2020));
    }


}

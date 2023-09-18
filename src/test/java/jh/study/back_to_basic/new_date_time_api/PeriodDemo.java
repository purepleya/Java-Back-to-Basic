package jh.study.back_to_basic.new_date_time_api;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

public class PeriodDemo {

    @Test
    void creationDemo() {
        Period period = Period.between(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2));
        System.out.println(period);

        Period period2 = Period.ofDays(10);
        Period period3 = Period.ofMonths(3);
        System.out.println(period2);
    }

    @Test
    void methodDemo() {
        Period period = Period.ofMonths(1).withDays(50);
        System.out.println(period.getMonths());
    }

}

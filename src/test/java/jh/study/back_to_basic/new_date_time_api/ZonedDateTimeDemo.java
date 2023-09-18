package jh.study.back_to_basic.new_date_time_api;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class ZonedDateTimeDemo {

    @Test
    void getZoneIds() {
        Set<String> allZoneIds = ZoneId.getAvailableZoneIds();
        allZoneIds.forEach(System.out::println);
    }


    @Test
    void creationDemo() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println(zonedDateTime);
    }
}

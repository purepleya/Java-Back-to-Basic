package jh.study.back_to_basic.guide_to_java_8_optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CreatingOptionalObjectDemo {

    @Test
    void whenCreatesEmptyOptional_thenCorrect() {
        Optional<String> emtpy = Optional.empty();
//        isPresent() 메소드는 Optional 객체가 값을 가지고 있는지 여부를 boolean 값으로 반환한다.
        assertFalse(emtpy.isPresent());
    }

    @Test
    void givenNonNull_whenCreatesNonNullable_thenCorrect() {
        Optional<String> opt = Optional.of("String value");
        assertTrue(opt.isPresent());
    }

    @Test
    void givenNull_whenThrowsErrorOnCreate_thenCorrect() {
        // of() 메소드는 null 값을 인자로 받으면 NullPointerException을 발생시킨다.
        assertThrows(NullPointerException.class, () -> Optional.of(null));
    }

    @Test
    void givenNoneNull_whenCreateNullable_thenCorrect() {
        Optional<String> opt = Optional.ofNullable("String value");
        assertTrue(opt.isPresent());

        // ofNullable() 메소드는 null 값을 인자로 받아도 NullPointerException을 발생시키지 않는다.
        String nullValue = null;
        Optional<String> opt2 = Optional.ofNullable(nullValue);
        assertFalse(opt2.isPresent());
    }


    @Test
    void givenOptional_whenIsPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("String value");
        assertTrue(opt.isPresent());

        opt = Optional.ofNullable(null);
        assertFalse(opt.isPresent());
    }

    @Test
    void givenAnEmptyOptional_thenIsEmptyBehavesAsExpected() {
        Optional<String> opt = Optional.of("String value");
        assertFalse(opt.isEmpty());

        opt = Optional.ofNullable(null);
        assertTrue(opt.isEmpty());
    }

    @Test
    void givenOptional_whenIfPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("String value");
        opt.ifPresent(val -> System.out.println(val));
    }

    @Test
    void whenOrElseWorks_thenCorrect() {
        String value = "String value";
        String valueReturned = Optional.ofNullable(value).orElse("default");
        assertEquals(value, valueReturned);

        String nullValue = null;
        String nullValueReturned = Optional.ofNullable(nullValue).orElse("default");
        assertEquals("default", nullValueReturned);
    }

    @Test
    void whenOrElseGetWorks_thenCorrect() {
        String value = "String value";
        String valueReturned = Optional.ofNullable(value).orElseGet(() ->"default");
        assertEquals(value, valueReturned);

        String nullValue = null;
        String nullValueReturned = Optional.ofNullable(nullValue).orElseGet(() -> "default");
        assertEquals("default", nullValueReturned);
    }

}

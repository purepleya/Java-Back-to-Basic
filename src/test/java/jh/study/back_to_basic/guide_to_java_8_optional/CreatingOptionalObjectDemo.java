package jh.study.back_to_basic.guide_to_java_8_optional;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
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


    public String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }


    @Test
    void whenOrElseGetAndOrElseOverlap_thenCorrect() {
        String text = null;

        System.out.println("Using orElseGet:");
        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Default Value", defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Default Value", defaultText);
    }

    @Test
    void whenOrElseGetAndOrElseDiffer_thenCorrect() {
        String text = "Text present";

        System.out.println("Using orElseGet:");
        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertEquals("Text present", defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertEquals("Text present", defaultText);
    }

    @Test
    void whenOrElseThrowWorks_thenCorect() {
        String nullValue = null;
        assertThrows(
                IllegalArgumentException.class,
                () -> Optional.ofNullable(nullValue).orElseThrow(IllegalArgumentException::new)
        );

        assertThrows(
                NoSuchElementException.class,
                () -> Optional.ofNullable(nullValue).orElseThrow()
        );
    }


    @Test
    void whenOptionalFilterWorks_thenCorrect() {
        Integer year = 2016;
        Optional<Integer> yearOptional = Optional.of(year);
        boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
        assertTrue(is2016);

        boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
        assertFalse(is2017);
    }


    private class Modem {
        private Double price;

        public Double getPrice() {
            return price;
        }

        public Modem(Double price) {
            this.price = price;
        }
    }

    public boolean priceIsInRange1(Modem modem) {
        if (modem != null && modem.getPrice() != null
                && (modem.getPrice() >= 10
                    && modem.getPrice() <= 15)) {
            return true;
        }

        return false;
    }

    @Test
    void whenFiltersWithoutOptional_thenCorrect() {
        assertTrue(priceIsInRange1(new Modem(10.0)));
        assertFalse(priceIsInRange1(new Modem(9.9)));
        assertFalse(priceIsInRange1(new Modem(null)));
        assertFalse(priceIsInRange1(new Modem(15.5)));
        assertFalse(priceIsInRange1(null));
    }

    public boolean priceIsInRange2(Modem modem) {
        return Optional.ofNullable(modem)
                .map(Modem::getPrice)
                .filter(p -> p >= 10)
                .filter(p -> p <= 15)
                .isPresent();
    }

    @Test
    public void whenFiltersWithOptional_thenCorrect() {
        assertTrue(priceIsInRange2(new Modem(10.0)));
        assertFalse(priceIsInRange2(new Modem(9.9)));
        assertFalse(priceIsInRange2(new Modem(null)));
        assertFalse(priceIsInRange2(new Modem(15.5)));
        assertFalse(priceIsInRange2(null));
    }

    @Test
    void givenOptional_whenMapWorksWithFilter_thenCorrect() {
        String password = " password ";
        Optional<String> passOpt = Optional.of(password);
        boolean correctPassword = passOpt
                .map(String::trim)
                .filter(pass -> pass.equals("password"))
                .isPresent();
        assertTrue(correctPassword);
    }



    private class Person {
        private String name;
        private int age;

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Integer> getAge() {
            return Optional.ofNullable(age);
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    void givenOptional_whenFlatMapWorks_thenCorrect() {
        Person person = new Person("john", 26);
        Optional<Person> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper = personOptional.map(Person::getName);
        Optional<String> nameOptional = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
        String name1 = nameOptional.orElse("");
        assertEquals("john", name1);

        String name = personOptional
                .flatMap(Person::getName)
                .orElse("");
        assertEquals("john", name);
    }




    private Optional<String> getEmpty() {
        System.out.println("get empty optional");
        return Optional.empty();
    }

    private Optional<String> getHello() {
        System.out.println("get hello optional");
        return Optional.of("hello");
    }

    private Optional<String> getBye() {
        System.out.println("get bye optional");
        return Optional.of("bye");
    }

    @Test
    void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturned() {
        Optional<String> found = Stream.of(getEmpty(), getHello(), getBye())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertEquals(getHello(), found);
    }

    @Test
    void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturnedAndRestNotEvaluated() {
        Optional<String> found = Stream.<Supplier<Optional<String>>>of(this::getEmpty, this::getHello, this::getBye)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        
        assertEquals(getHello(), found);
    }


    private Optional<String> createOptional(String input) {
        System.out.println("get input optional : " + String.valueOf(input));

        if (input == null || "".equals(input) || "empty".equals(input)) {
            return Optional.empty();
        }
        return Optional.of(input);
    }

    @Test
    void givenTwoOptionalsReturnedByOneArgMethod_whenChaining_thenFirstNonEmptyIsReturned() {
        Optional<String> found = Stream.<Supplier<Optional<String>>>of(
                        () -> createOptional("empty"),
                        () -> createOptional("hello")
                )
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertEquals(createOptional("hello"), found);
    }

    @Test
    void givenOptional_whenPresentAndEmpty_thenShouldTakeAValueFromItAndOr() {
        String expected = "properValue";
        Optional<String> value = Optional.of(expected);
        Optional<String> emptyValue = Optional.ofNullable(null);
        Optional<String> defaultValue = Optional.of("default");

        Optional<String> result = value.or(() -> defaultValue);
        assertEquals(expected, result.get());

        Optional<String> result2 = emptyValue.or(() -> defaultValue);
        assertEquals("default", result2.get());
    }


    @Test
    void givenOptional_whenPresentAndNotPresent_thenShouldExecuteProperCallback() {
        Optional<String> value = Optional.of("properValue");
        Optional<String> emptyValue = Optional.ofNullable(null);

        value.ifPresentOrElse(
                v -> System.out.println("properValue = " + v),
                () -> System.out.println("properValue is absent")
        );
        // properValue = properValue


        emptyValue.ifPresentOrElse(
                v -> System.out.println("emptyValue = " + v),
                () -> System.out.println("emptyValue is absent")
        );
        // emptyValue is absent
    }


    @Test
    void givenOptionalOfSome_whenToStream_thenShouldTreatItAsOneElementStream() {
        Optional<String> value = Optional.of("a");

        List<String> collect = value.stream().map(String::toUpperCase).collect(Collectors.toList());

        assertThat(collect).hasSameElementsAs(List.of("A"));
    }

}

package jh.study.back_to_basic.java_14_records;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecordDemo {

    // Record 이전
    public class PersonClass {

        private final String name;
        private final int age;

        public PersonClass(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    public class Contact {
        private String office;
        private String home;

        public String getOffice() {
            return office;
        }
        public void setOffice(String office) {
            this.office = office;
        }

        public String getHome() {
            return home;
        }
        public void setHome(String home) {
            this.home = home;
        }

        public Contact(String office, String home) {
            this.office = office;
            this.home = home;
        }
    }

    // Record 이후
    public record PersonRecord(String name, int age, Contact contact) {
    }

    public record SimplePersonRecord(String name, int age) {
        public void print() {
            System.out.println("name: " + name + ", age: " + age);
        }
    }


    @Test
    @DisplayName("필드값은 변경이 불가 하지만 필드에 정의된 필드가 속성을 가지는 객체 타입인 경우 필드의 속성값은 변경이 가능하다.")
    void recordFieldsAreShallowImmutables() {
        PersonRecord person = new PersonRecord("John", 20, new Contact("02", "010"));

        // person.name = "John2"; // 컴파일 에러
        person.contact.setHome("070");
        assertEquals("070", person.contact.getHome());
    }

    @Test
    @DisplayName("Record의 필드는 필드 이름이 함수명인 함수로 접근이 가능하다. (필드에 직접 접근도 가능하다.)")
    void canAcesseRecordFieldsByFieldName() {
        PersonRecord person = new PersonRecord("John", 20, new Contact("02", "010"));

        // 필드명으로 바로 접근 가능 - 가급적이면 함수를 이용하자
        assertEquals("John", person.name);

        // 필드명과 동일한 함수로 접근 - 캡슐화를 위해 함수를 이용하자
        assertEquals(20, person.age());

        assertEquals("02", person.contact().getOffice());
        assertEquals("010", person.contact().getHome());
    }

    @Test
    @DisplayName("toString 테스트")
    void toStringTest() {
        SimplePersonRecord person = new SimplePersonRecord("John", 20);
        assertEquals("SimplePersonRecord[name=John, age=20]", person.toString());
    }


    @Test
    @DisplayName("equals, hashCode 테스트")
    void equalsAndHashCodeTest() {
        SimplePersonRecord simplePerson = new SimplePersonRecord("John", 20);
        SimplePersonRecord simplePerson2 = new SimplePersonRecord("John", 20);
        assertEquals(simplePerson, simplePerson2);

        Contact defaultContact = new Contact("02", "010");
        PersonRecord person = new PersonRecord("John", 20, defaultContact);
        PersonRecord person2 = new PersonRecord("John", 20, new Contact("02", "010"));

        // person과 person2는 서로 다른 Contact 가진다.
        assertNotEquals(person, person2);

        // person과 person3는 같은 Contact를 가진다.
        PersonRecord person3 = new PersonRecord("John", 20, defaultContact);

        assertEquals(person, person3);
        assertTrue(person.equals(person3));

        assertEquals(person.hashCode(), person3.hashCode());
    }

    @Test
    @DisplayName("Constructor 에 validation 추가")
    void constructorValidationTest() {
        assertThrows(IllegalArgumentException.class, () -> new SimplePersonRecord("John", -1));
    }


    @Test
    @DisplayName("Record Serialization, Deserialization Test")
    void recordSerializationTest() throws Exception {
        SimplePersonRecord person = new SimplePersonRecord("John", 20);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);

        System.out.println(json);
        assertEquals("{\"name\":\"John\",\"age\":20}", json);

        SimplePersonRecord person2 = mapper.readValue(json, SimplePersonRecord.class);
        assertEquals(person, person2);
    }

}

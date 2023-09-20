package jh.study.back_to_basic;

import org.junit.jupiter.api.Test;

public class java21_string_templates {

    @Test
    void textBlockDemo() {
        String name = """
                Hello~~!!!
                My name is JH
                """;

        System.out.println(name);
    }

    void stringTemplateDemo() {
        String name = "Joan";
//        String info = STR."My name is \{name}";
        assert info.equals("My name is Joan");
    }
}

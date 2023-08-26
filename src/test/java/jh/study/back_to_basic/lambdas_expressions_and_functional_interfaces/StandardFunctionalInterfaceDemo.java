package jh.study.back_to_basic.lambdas_expressions_and_functional_interfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardFunctionalInterfaceDemo {

    @FunctionalInterface
    public interface Foo {
        String method(String string);
    }

    public String addViaCustomInterface(String string, Foo foo) {
        return foo.method(string);
    }

    @Test
    @DisplayName("함수형 인터페이스 Foo를 정의해서 구현하는 방법")
    public void implementCustomFunctionalInterface() {
        StandardFunctionalInterfaceDemo demo = new StandardFunctionalInterfaceDemo();
        Foo foo = parameter -> parameter + " from Custom Functional Interface";
        String result = demo.addViaCustomInterface("Message", foo);
        assertEquals("Message from Custom Functional Interface", result);
    }


    public String addViaStandardInterface(String string, Function<String, String> fn) {
        return fn.apply(string);
    }

    @Test
    @DisplayName("표준 함수형 인터페이스인 Function을 이용해서 구현한 방법")
    public void implementsStandardFunctionalInterface() {
        StandardFunctionalInterfaceDemo demo = new StandardFunctionalInterfaceDemo();
        Function<String, String> fn = parameter -> parameter + " from Standard Functional Interface";
        String result = demo.addViaStandardInterface("Message", fn);
        assertEquals("Message from Standard Functional Interface", result);
    }

}

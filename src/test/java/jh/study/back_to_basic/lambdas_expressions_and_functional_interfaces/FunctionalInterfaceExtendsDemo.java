package jh.study.back_to_basic.lambdas_expressions_and_functional_interfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FunctionalInterfaceExtendsDemo {

    @FunctionalInterface
    public interface Baz {
        String method(String string);
        default String defaultBaz() {
            return "defaultBaz";
        }
    }

    @FunctionalInterface
    public interface Bar {
        String method(String string);
        default String defaultBar() {
            return "defaultBar";
        }
    }

    @FunctionalInterface
    public interface Foo extends Bar, Baz {
//        Baz 와 Bar의 추상 메소드가 모두 "method"라는 같은 이름을 사용하기 때문에 오류가 발생하지 않는다.
    }

    @Test
    @DisplayName("함수형 인터페이스 상속 테스트")
    void extendedFunctionalInterface() {
        Foo foo = parameter -> parameter + " from Foo";
        String result = foo.method("Message");
        System.out.println(result);
    }

    @FunctionalInterface
    public interface Baz2 {
        String method(String string);
        default String commonMethod() {
            return "common method of Baz2";
        }
    }

    @FunctionalInterface
    public interface Bar2 {
        String method(String string);
        default String commonMethod() {
            return "common method of Baz2";
        }
    }

    @FunctionalInterface
    public interface Foo2 extends Bar2, Baz2 {
//        Baz2 와 Bar2에 commonMethod 메소드가 개별로 정의 되어서 아래와 같은 오류가 발생한다.
//        Foo2 inherits unrelated defaults for commonMethod() from types Bar2 and Baz2
//        따라서 아래 처럼 문제가 되는 commonMethod를 재 정의 해 주는 것으로 해결 할 수 있다.
        default String commonMethod() {
            return "common method of Foo2";
        }
    }

    @Test
    @DisplayName("함수형 인터페이스 상속 테스트2")
    void extendedFunctionalInterface2() {
        Foo foo = parameter -> parameter + " from Foo2";
        String result = foo.method("Message");
        System.out.println(result);
    }

}

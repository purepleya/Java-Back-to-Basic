package jh.study.back_to_basic.lambdas_expressions_and_functional_interfaces;

import java.util.function.Consumer;

public class EffectivelyFinalDemo {

    /*
    public void method() {
        String localVariable = "Local";

        Consumer<String> foo = parameter -> {
//            에러 발생 - Variable used in lambda expression should be final or effectively final
            localVariable = parameter;
            return localVariable;
        };
    }
    */

    class ParameterType {
        private String value;

        public void append(String str) {
            this.value += str;
        }
    }

    public void method2() {
        ParameterType effectivelyFinal = new ParameterType();
        Consumer<ParameterType> foo = parameter -> {
//            변수의 값이 아닌 객체 변수의 값은 변경이 가능하다.
            parameter.append(" from lambda");
        };
    }

}

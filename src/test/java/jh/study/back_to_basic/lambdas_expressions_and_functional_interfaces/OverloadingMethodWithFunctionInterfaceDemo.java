package jh.study.back_to_basic.lambdas_expressions_and_functional_interfaces;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OverloadingMethodWithFunctionInterfaceDemo {

    public class Process {
        public String process(Callable<String> c) throws Exception {
            return c.call();
        }

        public String process(Supplier<String> s) {
            return s.get();
        }
    }

    /*
    @Test
    void testOverloading() {
        Process process = new Process();
//        아래와 같은 에러가 발생한다.
//        Ambiguous method call. Both process(Callable<String>) in Process and process (Supplier<String>) in Process match
        String result = process.process(() -> "abc");
        assertEquals("abc", result);
    }
    */
    public class Process2 {
        public String processWithCallable(Callable<String> c) throws Exception {
            return c.call();
        }

        public String processWithSupplier(Supplier<String> s) {
            return s.get();
        }
    }

    @Test
    void testOverloading2() {
        Process2 process = new Process2();
        String result = process.processWithSupplier(() -> "abc");
        assertEquals("abc", result);
    }


}

package jh.study.back_to_basic.idioms;

import org.junit.jupiter.api.Test;

import java.util.*;

public class InitializeCollectionDemo {

    @Test
    public void test() {
//        List<String> list = List.of("a", "b", "c");
//        Set<String> set = Set.of("a", "b", "c");
//
//        list.add("d"); // java.lang.UnsupportedOperationException
//        set.add("d"); // java.lang.UnsupportedOperationException

        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Set<String> set = new HashSet<>(Arrays.asList("a", "b", "c"));

        list.add("d");
        set.add("d");

        System.out.println(list); // [a, b, c, d]
        System.out.println(set);  // [a, b, c, d]
    }
}

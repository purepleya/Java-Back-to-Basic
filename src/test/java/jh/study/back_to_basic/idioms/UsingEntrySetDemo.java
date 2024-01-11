package jh.study.back_to_basic.idioms;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UsingEntrySetDemo {

    @Test
    public void test() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        Set<String> keys = map.keySet();
        for(String key : keys) {
            String value = map.get(key);
            System.out.println(key + " : " + value);
        }

        Set<Entry<String, String>> entirySet = map.entrySet();
        for (Entry<String, String> e: entirySet) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }

}

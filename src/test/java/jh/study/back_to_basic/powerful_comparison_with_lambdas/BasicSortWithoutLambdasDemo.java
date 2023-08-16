package jh.study.back_to_basic.powerful_comparison_with_lambdas;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BasicSortWithoutLambdasDemo {

    @Test
    @Order(0)
    void givenPreLambda_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<>();
        humans.add(new Human("Sarah", 10));
        humans.add(new Human("Jack", 12));

        Collections.sort(humans, new Comparator<Human>() {
            @Override
            public int compare(Human h1, Human h2) {
                return h1.getName().compareTo(h2.getName());
            }
        });

        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }


    @Test
    @Order(1)
    void whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<>();
        humans.add(new Human("Sarah", 10));
        humans.add(new Human("Jack", 12));

        humans.sort((Human h1, Human h2) -> h1.getName().compareTo(h2.getName()));

        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }


    @Test
    @Order(2)
    void givenLambdaShortForm_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<>();
        humans.add(new Human("Sarah", 10));
        humans.add(new Human("Jack", 12));

        humans.sort((h1, h2) -> h1.getName().compareTo(h2.getName()));

        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }


    @Test
    @Order(3)
    void givenMethodDefinition_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        List<Human> humans = Arrays.asList(
                new Human("Sarah", 10),
                new Human("Jack", 12)
        );

        humans.sort(Human::compareByNameThenAge);
        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }


    @Test
    @Order(4)
    void givenInstanceMethod_whenSortingEntitiesByName_thenCorrectlySorted() {

        List<Human> humans = Arrays.asList(
                new Human("Sarah", 10),
                new Human("Jack", 12)
        );

        Collections.sort(humans, Comparator.comparing(Human::getName));
        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }


    @Test
    @Order(5)
    void whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        List<Human> humans = Arrays.asList(
                new Human("Sarah", 10),
                new Human("Jack", 12)
        );

        Comparator<Human> comparator = (h1, h2) -> h1.getName().compareTo(h2.getName());

        humans.sort(comparator.reversed());

        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public void whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        List<Human> humans = Arrays.asList(
                new Human("Sarah", 12),
                new Human("Sarah", 10),
                new Human("Zack", 12)
        );

        humans.sort((lhs, rhs) -> {
            if (lhs.getName().equals(rhs.getName())) {
                return Integer.compare(lhs.getAge(), rhs.getAge());
            } else {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

}

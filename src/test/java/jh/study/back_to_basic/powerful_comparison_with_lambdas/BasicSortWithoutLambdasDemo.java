package jh.study.back_to_basic.powerful_comparison_with_lambdas;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


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

//        Collections.sort(humans, Comparator.comparing(Human::getName));
        humans.sort(Comparator.comparing(Human::getName));
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
    @Order(6)
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


    @Test
    @Order(7)
    public void givenComposition_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {

        List<Human> humans = Arrays.asList(
                new Human("Sarah", 12),
                new Human("Sarah", 10),
                new Human("Zack", 12)
        );

        humans.sort(
                Comparator.comparing(Human::getName).thenComparing(Human::getAge)
        );

        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    @Order(8)
    void givenStreamNaturalOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<String> letters = Arrays.asList("B", "A", "C");

        List<String> sortedLetters = letters.stream().sorted().collect(Collectors.toList());
        assertThat(sortedLetters.get(0), equalTo("A"));
    }

    @Test
    @Order(9)
    void givenStreamCustomOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = Arrays.asList(new Human("Sarah", 10), new Human("Jack", 12));
        Comparator<Human> nameComparator = (h1, h2) -> h1.getName().compareTo(h2.getName());

        List<Human> sortedHumans =
                humans.stream().sorted(nameComparator).collect(Collectors.toList());
        assertThat(sortedHumans.get(0), equalTo(new Human("Jack", 12)));
        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    @Order(10)
    void givenStreamComparatorOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = Arrays.asList(new Human("Sarah", 10), new Human("Jack", 12));

        List<Human> sortedHumans = humans.stream()
                .sorted(Comparator.comparing(Human::getName))
                .collect(Collectors.toList());

        assertThat(sortedHumans.get(0), equalTo(new Human("Jack", 12)));
        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }


    @Test
    @Order(11)
    void givenStreamNaturalOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        List<String> letters = Arrays.asList("B", "A", "C");

        List<String> reverseSortedLetters = letters.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        assertThat(reverseSortedLetters.get(0), equalTo("C"));
        assertThat(letters.get(0), equalTo("B"));
    }

    @Test
    @Order(12)
    void givenStreamCustomOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        List<Human> humans = Arrays.asList(new Human("Sarah", 10), new Human("Jack", 12));
        Comparator<Human> reverseNameComparator =
                (h1, h2) -> h2.getName().compareTo(h1.getName());

        List<Human> reverseSortedHumans = humans.stream().sorted(reverseNameComparator).collect(Collectors.toList());
        assertThat(reverseSortedHumans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    @Order(13)
    void givenStreamComparatorOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        List<Human> humans = Arrays.asList(new Human("Sarah", 10), new Human("Jack", 12));

        List<Human> reverseSortedHumans = humans.stream()
                .sorted(Comparator.comparing(Human::getName, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        assertThat(reverseSortedHumans.get(0), equalTo(new Human("Sarah", 10)));
    }


    @Test
    @Order(14)
    public void givenANullElement_whenSortingEntitiesByName_thenThrowsNPE() {
        List<Human> humans = Arrays.asList(null, new Human("Jack", 12));
        assertThrows(NullPointerException.class, () -> {
            humans.sort((h1, h2) -> h1.getName().compareTo(h2.getName()));
        });
    }


    @Test
    @Order(15)
    void givenANullElement_whenSortingEntitiesByNameManually_thenMovesTheNullToLast() {
        List<Human> humans = Arrays.asList(null, new Human("Jack", 12), null);

        humans.sort((h1, h2) -> {
            if (h1 == null) {
                return h2 == null ? 0 : 1;
            }
            else if (h2 == null) {
                return -1;
            }
            return h1.getName().compareTo(h2.getName());
        });

        assertNotNull(humans.get(0));
        assertNull(humans.get(1));
        assertNull(humans.get(2));
    }

    @Test
    @Order(16)
    void givenANullElement_whenSortingEntitiesByName_thenMovesTheNullToLast() {
        List<Human> humans = Arrays.asList(null, new Human("Jack", 12), null);

        humans.sort(Comparator.nullsLast(Comparator.comparing(Human::getName)));

        assertNotNull(humans.get(0));
        assertNull(humans.get(1));
        assertNull(humans.get(2));
    }


    @Test
    @Order(17)
    void givenANullElement_whenSortingEntitiesByName_thenMovesTheNullToStart() {
        List<Human> humans = Arrays.asList(null, new Human("Jack", 12), null);

        humans.sort(Comparator.nullsFirst(Comparator.comparing(Human::getName)));

        assertNull(humans.get(0));
        assertNull(humans.get(1));
        assertNotNull(humans.get(2));
    }

}

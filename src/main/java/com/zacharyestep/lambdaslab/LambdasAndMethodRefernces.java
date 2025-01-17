package com.zacharyestep.lambdaslab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.*;

public class LambdasAndMethodRefernces {

    public static void main(String[] args) {
        staticMR();
        boundMR();
        unboundMR();
        constructorMR();
    }

    public static void staticMR() {
        var listOfIntegers = new ArrayList<>(List.of(1, 2, 4, 7, 5));
        Consumer<List<Integer>> sortingLambda = (i) -> Collections.sort(i);
        sortingLambda.accept(listOfIntegers);
        assert listOfIntegers.equals(List.of(1, 2, 4, 5, 7));
        listOfIntegers = new ArrayList<>(List.of(1, 2, 4, 7, 5));
        Consumer<List<Integer>> sortingMethodReference = Collections::sort;
        sortingMethodReference.accept(listOfIntegers);
        assert listOfIntegers.equals(List.of(1, 2, 4, 5, 7));
    }

    public static void boundMR() {
        var name = "Mr Joe Bloggs";
        Predicate<String> startsWith = (s) -> name.startsWith(s);
        assert startsWith.test("Mr");
        assert !startsWith.test("Mrs");
        startsWith = name::startsWith;
        assert startsWith.test("Mr");
        assert !startsWith.test("Mrs");
    }

    public static void unboundMR() {
        Predicate<String> isEmpty = (s) -> s.isEmpty();
        assert isEmpty.test("");
        assert !isEmpty.test("not empty");
        isEmpty = String::isEmpty;
        assert isEmpty.test("");
        assert !isEmpty.test("not empty");

        BiPredicate<String, String> startsWith = (s1, s2) -> s1.startsWith(s2);
        assert startsWith.test("Mr Joe Bloggs", "Mr");
        assert !startsWith.test("Mr Joe Bloggs", "Mrs");
        startsWith = String::startsWith;

        assert startsWith.test("Mr Joe Bloggs", "Mr");
        assert !startsWith.test("Mr Joe Bloggs", "Mrs");
    }

    public static void constructorMR() {
        Supplier<List<String>> listSupplier = () -> new ArrayList<String>();
        var list = listSupplier.get();
        list.add("Lambda");
        System.out.println(Arrays.toString(list.toArray()));
        listSupplier = ArrayList::new;
        list = listSupplier.get();
        list.add("Method Reference");
        System.out.println(Arrays.toString(list.toArray()));
        Function<Integer, List<Integer>> withCapacity = (i) -> new ArrayList<>(i);
        var list2 = withCapacity.apply(10);
        System.out.println(Arrays.toString(list2.toArray()));
        withCapacity = ArrayList::new;
        list2 = withCapacity.apply(10);
        System.out.println(Arrays.toString(list2.toArray()));
    }
}

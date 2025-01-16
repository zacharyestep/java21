package com.zacharyestep.lambdaslab;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

interface Printable<T> {
    void print(T t);
}

interface Retrievable<T> {
    T retrieve();
}

interface Evaluate<T> {
    boolean evaluate(T t);
}

interface Functionable<T, R> {
    R applyThis(T t);
}

class Person {
    private final int age;
    private final String name;
    private final Double height;

    Person(int age, String name, Double height) {
        this.age = age;
        this.name = name;
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public Double getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return String.format("%s is %d years old and is %.2f meters tall.", name, age, height);
    }
}


public class BasicLambdas {
    public static void main(String[] args) {
        consumer();
        supplier();
        predicate();
        function();
        var people = getPeople();
        sortAge(people);
        people.forEach(System.out::println);
        sortName(people);
        people.forEach(System.out::println);
        sortHeight(people);
        people.forEach(System.out::println);
    }

    private static void sortHeight(List<Person> people) {
        people.sort(Comparator.comparing(Person::getHeight));
    }

    private static void sortName(List<Person> people) {
        people.sort(Comparator.comparing(Person::getName));
    }

    private static void sortAge(List<Person> people) {
        people.sort(Comparator.comparing(Person::getAge));
    }

    private static List<Person> getPeople() {
        List<Person> result = new ArrayList<>();
        result.add(new Person(33, "Mike", 1.8));
        result.add(new Person(25, "Mary", 1.4));
        result.add(new Person(34, "Alan", 1.7));
        result.add(new Person(30, "Zoe", 1.5));
        return result;
    }

    private static void function() {
        Functionable<Integer, String> numberIsLambda = (s) -> "Number is:%d".formatted(s);
        numberIsLambda.applyThis(25);
        Functionable<Integer, String> numberIsFR = "Number is:%d"::formatted;
        numberIsFR.applyThis(25);
        Function<Integer, String> numberIsLambdaFUnction = (s) -> "Number is:%d".formatted(s);
        numberIsLambdaFUnction.apply(25);
        Function<Integer, String> numberIsFRFunction = "Number is:%d"::formatted;
        numberIsFRFunction.apply(25);

    }

    private static <T, R extends Predicate<T>> boolean check(T t, R r) {
        return r.test(t);
    }

    private static void predicate() {
        Evaluate<Integer> evaluator = (i) -> i < 0;
        evaluator.evaluate(-1);
        evaluator.evaluate(1);

        Predicate<Integer> predicate = (i) -> i < 0;
        predicate.test(-1);
        predicate.test(1);

        Predicate<Integer> isEven = (i) -> i % 2 == 0;
        check(4, isEven);
        check(7, isEven);

        Predicate<String> startsWithMr = (s) -> s.startsWith("Mr.");
        startsWithMr.test("Mr. Joe Bloggs");
        startsWithMr.test("Mrs. Ann  Bloggs ");

        Predicate<Person> isAdult = (p) -> p.getAge() >= 18;
        isAdult.test(new Person(33, "Mike", 1.8));
        isAdult.test(new Person(13, "Ann", 1.4));
    }

    private static void supplier() {
        Retrievable<Integer> retrievableInteger = () -> 77;
        retrievableInteger.retrieve();
        Supplier<Integer> supplierInteger = () -> 77;
        supplierInteger.get();
    }

    private static void consumer() {
        Printable<String> stringPinter = System.out::println;
        stringPinter.print("Printable lambda");

        Consumer<String> stringPrinterConsumer = System.out::println;
        stringPrinterConsumer.accept("Printable lambda");

        Consumer<String> stringPrinterLambda = (s) -> System.out.println(s);
        stringPrinterLambda.accept("Printable lambda");
    }
}
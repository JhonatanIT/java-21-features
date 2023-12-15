package org.example;

sealed interface S permits A {
}

record A(int x, int y) implements S {
}

enum Color {RED, GREEN, BLUE}

public class JEP440RecordPatterns {
    public static void main(String[] args) {
        S o = new A(5, 1);

        int value = switch (o) {
            case A(int x, _) when x >= 10 -> x;
            case A(int x, int y) when x <= 0 -> y;
            default -> -1;
        };

        System.out.println(value);

        var c = Color.RED;
        var result = switch (c) {
            case RED -> 0;
            case GREEN -> 1;
            case BLUE -> 2;
        };


    }
}

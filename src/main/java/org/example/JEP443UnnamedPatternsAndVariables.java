package org.example;

public class JEP443UnnamedPatternsAndVariables {

    public static void main(String[] args) {
        S o = new A(0, 1);

        if (o instanceof A(int x, _)) {
            System.out.println(x);
        }

//        A[] as = new A[]{new A(0, 0), new A(1, 1)};
//
//        for (A _ : as) {
//            System.out.println("Hello");
//        }
//
//        try {
//            int i = Integer.parseInt("a");
//        } catch (NumberFormatException _) {
//            System.out.println("Bad number");
//        }
    }
}

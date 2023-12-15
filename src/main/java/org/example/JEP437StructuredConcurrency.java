package org.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class JEP437StructuredConcurrency {

    static String getUser() throws InterruptedException {
        Thread.sleep(3_000);
        return "Anton";
    }

    static Integer getOrder() throws InterruptedException {
        Thread.sleep(2_000);
//    return 10;
        return 10 / 0;
    }


    public static void main(String[] args) throws InterruptedException {

//        //No concurrent
//        String theUser  = getUser();
//        int theOrder = getOrder();
//        System.out.println(STR."\{theUser}: \{theOrder}");

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask<String> user = scope.fork(JEP437StructuredConcurrency::getUser);
            Subtask<Integer> order = scope.fork(JEP437StructuredConcurrency::getOrder);

            scope.join();           // Join both forks
            scope.throwIfFailed();  // ... and propagate errors

            // Here, both forks have succeeded, so compose their results
            System.out.println(STR."\{user.get()}: \{order.get()}");
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }
}

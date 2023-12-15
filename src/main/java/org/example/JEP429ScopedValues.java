package org.example;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class JEP429ScopedValues {

    static final ScopedValue<String> sv = ScopedValue.newInstance();

    static String getUser() {
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException _) {
        }

        //Rebinding the scoped value (no affect the current scopedValue):
        ScopedValue.where(sv, "Non Anton").run(() -> System.out.println(sv.get()));
        return sv.get();
    }

    static Integer getOrder() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException _) {
        }
        return 1;
    }

    /**
     * Advantages of Scoped Values:
     * <p>
     * They are only valid during the lifetime of the Runnable passed to the where method.
     * A scoped value is immutable - it can only be reset for a new scope by rebinding.
     * The child threads created by StructuredTaskScope have access to the scoped value of the parent thread.
     *
     */
    public static void main(String[] args) {

        ScopedValue.runWhere(sv, "anton", () -> {
            try {
                try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                    Subtask<String> user = scope.fork(JEP429ScopedValues::getUser);
                    Subtask<Integer> order = scope.fork(JEP429ScopedValues::getOrder);

                    scope.join();
                    scope.throwIfFailed();

                    System.out.println(STR."\{user.get()}: \{order.get()}");
                }
            } catch (Exception _) {
            }
        });
        ScopedValue.where(sv, "anton").run(() -> System.out.println(sv.get()));
    }

}

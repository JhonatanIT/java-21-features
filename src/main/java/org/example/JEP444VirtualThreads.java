package org.example;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class JEP444VirtualThreads {

    public static void main(String[] args) {

        Thread.ofVirtual().start(() -> System.out.println(Thread.currentThread()));
        Thread.ofPlatform().start(() -> System.out.println(Thread.currentThread()));

        System.out.println("Platform thread");
        try (var executor = Executors.newFixedThreadPool(1_000)) {
            IntStream.range(0, 10_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(1000);
                return i;
            }));
        }

        //Virtual threads are better performance with I/O interruptions
        System.out.println("Virtual Thread");
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(1000);
                return i;
            }));
        }
    }
}

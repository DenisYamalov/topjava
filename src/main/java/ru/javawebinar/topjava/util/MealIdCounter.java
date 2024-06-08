package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class MealIdCounter {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static int getId() {
        return counter.incrementAndGet();
    }
}

package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealDayCounter {
    private final LocalDateTime dateTime;
    private final int caloriesPerDay;
    private int totalCalories;
    private boolean excess;

    public MealDayCounter(int caloriesPerDay, LocalDateTime dateTime) {
        this.caloriesPerDay = caloriesPerDay;
        this.dateTime = dateTime;
    }

    public boolean isExcess() {
        return excess;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void addCalories(int calories) {
        totalCalories += calories;
        excess = totalCalories > caloriesPerDay;
    }
}

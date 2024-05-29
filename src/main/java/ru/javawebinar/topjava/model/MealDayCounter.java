package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MealDayCounter {
    private final LocalDate date;
    private final int caloriesPerDay;
    private int totalCalories;
    private boolean excess;

    public MealDayCounter(int caloriesPerDay, LocalDate date) {
        this.caloriesPerDay = caloriesPerDay;
        this.date = date;
    }

    public boolean isExcess() {
        return excess;
    }

    public LocalDate getDate() {
        return date;
    }

    public void addCalories(int calories) {
        totalCalories += calories;
        excess = totalCalories > caloriesPerDay;
    }
}

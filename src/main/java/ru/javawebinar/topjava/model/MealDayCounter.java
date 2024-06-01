package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.util.Objects;

public class MealDayCounter {
    private final LocalDate date;
    private final int caloriesPerDay;
    private int totalCalories;
    private boolean excess;

    public MealDayCounter(int caloriesPerDay, LocalDate date) {
        this.caloriesPerDay = caloriesPerDay;
        this.date = date;
    }

    public MealDayCounter(int caloriesPerDay, LocalDate date, int totalCalories) {
        this.caloriesPerDay = caloriesPerDay;
        this.date = date;
        this.totalCalories = totalCalories;
    }

    public boolean isExcess() {
        return excess;
    }

    public MealDayCounter addCalories(int calories) {
        totalCalories += calories;
        excess = totalCalories > caloriesPerDay;
        return this;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealDayCounter)) return false;
        MealDayCounter that = (MealDayCounter) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}

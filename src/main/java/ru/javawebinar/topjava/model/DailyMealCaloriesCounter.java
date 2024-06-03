package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.util.Objects;

public class DailyMealCaloriesCounter {
    private final LocalDate date;
    private final int caloriesPerDay;
    private int totalCalories;
    private boolean excess;

    public DailyMealCaloriesCounter(int caloriesPerDay, LocalDate date) {
        this.caloriesPerDay = caloriesPerDay;
        this.date = date;
    }

    public DailyMealCaloriesCounter(int caloriesPerDay, LocalDate date, int totalCalories) {
        this.caloriesPerDay = caloriesPerDay;
        this.date = date;
        this.totalCalories = totalCalories;
    }

    public boolean isExcess() {
        return excess;
    }

    public DailyMealCaloriesCounter addCalories(int calories) {
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
        if (!(o instanceof DailyMealCaloriesCounter)) return false;
        DailyMealCaloriesCounter that = (DailyMealCaloriesCounter) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }

    @Override
    public String toString() {
        return "DailyMealCaloriesCounter{" +
                "date=" + date +
                ", caloriesPerDay=" + caloriesPerDay +
                ", totalCalories=" + totalCalories +
                ", excess=" + excess +
                '}';
    }
}

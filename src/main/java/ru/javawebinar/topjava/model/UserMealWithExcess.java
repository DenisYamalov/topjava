package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final DailyMealCaloriesCounter dailyMealCaloriesCounter;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this(dateTime, description, calories, new DailyMealCaloriesCounter(excess ? 0 : calories + 1, dateTime.toLocalDate()));
        dailyMealCaloriesCounter.addCalories(calories);
    }

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, DailyMealCaloriesCounter dailyMealCaloriesCounter) {
        this.calories = calories;
        this.dateTime = dateTime;
        this.description = description;
        this.dailyMealCaloriesCounter = dailyMealCaloriesCounter;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + dailyMealCaloriesCounter.isExcess() +
                '}';
    }
}

package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final MealDayCounter mealDayCounter;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this(dateTime,description,calories,new MealDayCounter(excess?0:calories+1, dateTime.toLocalDate()));
        mealDayCounter.addCalories(calories);
    }

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, MealDayCounter mealDayCounter) {
        this.calories = calories;
        this.dateTime = dateTime;
        this.description = description;
        this.mealDayCounter = mealDayCounter;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + mealDayCounter.isExcess() +
                '}';
    }
}

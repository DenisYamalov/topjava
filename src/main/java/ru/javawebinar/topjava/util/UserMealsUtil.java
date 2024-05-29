package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.MealDayCounter;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        System.out.println();
        System.out.println("Optimized cycle:");
        System.out.println(filteredByCyclesOptimized(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals,
                                                            LocalTime startTime,
                                                            LocalTime endTime,
                                                            int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        for (UserMeal meal : meals) {
            caloriesMap.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }

        for (UserMeal meal : meals) {
            boolean excess = caloriesMap.get(meal.getDate()) > caloriesPerDay;

            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                userMealWithExcesses.add(getUserMealWIthExcess(meal, excess));
            }

        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByCyclesOptimized(List<UserMeal> meals,
                                                                     LocalTime startTime,
                                                                     LocalTime endTime,
                                                                     int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDate, MealDayCounter> counterMap = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDate();
            MealDayCounter dayCounter = counterMap.getOrDefault(mealDate, new MealDayCounter(caloriesPerDay,
                    mealDate));
            dayCounter.addCalories(meal.getCalories());
            counterMap.put(mealDate, dayCounter);
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                UserMealWithExcess mealWithExcess = new UserMealWithExcess(meal, dayCounter);
                mealWithExcess.updateExcess();
                userMealWithExcesses.add(mealWithExcess);

            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals,
                                                             LocalTime startTime,
                                                             LocalTime endTime,
                                                             int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = meals.stream()
                .collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum));
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> getUserMealWIthExcess(meal, caloriesMap.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess getUserMealWIthExcess(UserMeal meal, boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}

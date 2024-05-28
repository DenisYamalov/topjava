package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

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
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDateTime, List<UserMeal>> mealMap = new HashMap<>();
        Map<LocalDateTime, Integer> caloriesMap = new HashMap<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getLocalTime(), startTime, endTime)) {
                List<UserMeal> mealList = mealMap.getOrDefault(meal.getDateTime(), new ArrayList<>());
                mealList.add(meal);
                mealMap.put(meal.getDateTime(), mealList);
                caloriesMap.merge(meal.getDateTime(), meal.getCalories(), Integer::sum);
            }
        }
        for (Map.Entry<LocalDateTime, List<UserMeal>> entry : mealMap.entrySet()) {
            boolean excess = caloriesMap.get(entry.getKey()) > caloriesPerDay;

            for (UserMeal meal : entry.getValue()) {
                userMealWithExcesses.add(new UserMealWithExcess(meal, excess));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDateTime, Integer> caloriesMap = new HashMap<>();
        Map<LocalDateTime, List<UserMeal>> mealMap = meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getLocalTime(), startTime, endTime))
                .peek(meal -> caloriesMap.put(meal.getDateTime(), meal.getCalories()))
                .collect(Collectors.groupingBy(UserMeal::getDateTime));
        return mealMap.values().stream()
                .flatMap(Collection::stream)
                .map(meal -> new UserMealWithExcess(meal, caloriesMap.get(meal.getDateTime()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}

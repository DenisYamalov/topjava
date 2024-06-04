package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.DailyMealCaloriesCounter;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
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
        System.out.println();
        System.out.println("Filter by cycles:");
        mealsTo.forEach(System.out::println);

        System.out.println();
        System.out.println("Filter by streams:");
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        System.out.println();
        System.out.println("Single cycle:");
        System.out.println(filteredByCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        System.out.println();
        System.out.println("Single stream:");
        System.out.println(filteredByStream(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals,
                                                            LocalTime startTime,
                                                            LocalTime endTime,
                                                            int caloriesPerDay) {
        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        for (UserMeal meal : meals) {
            dateCaloriesMap.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                boolean excess = dateCaloriesMap.get(meal.getDate()) > caloriesPerDay;
                userMealWithExcesses.add(getUserMealWithExcess(meal, excess));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByCycle(List<UserMeal> meals,
                                                           LocalTime startTime,
                                                           LocalTime endTime,
                                                           int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDate, DailyMealCaloriesCounter> counterMap = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDate();
            DailyMealCaloriesCounter dailyMealCaloriesCounter = counterMap.computeIfAbsent(mealDate,
                    localDate -> new DailyMealCaloriesCounter(caloriesPerDay, mealDate));
            dailyMealCaloriesCounter.addCalories(meal.getCalories());
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                UserMealWithExcess mealWithExcess = getUserMealWithExcess(meal, dailyMealCaloriesCounter);
                userMealWithExcesses.add(mealWithExcess);
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals,
                                                             LocalTime startTime,
                                                             LocalTime endTime,
                                                             int caloriesPerDay) {
        Map<LocalDate, Integer> dateCaloriesMap = meals.stream()
                .collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum));
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> getUserMealWithExcess(meal, dateCaloriesMap.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStream(List<UserMeal> meals,
                                                            LocalTime startTime,
                                                            LocalTime endTime,
                                                            int caloriesPerDay) {
        return meals.stream()
                .collect(Collector.of(
                        () -> new AbstractMap.SimpleImmutableEntry<>(
                                new HashMap<LocalDate, DailyMealCaloriesCounter>(), new ArrayList<UserMealWithExcess>()),
                        (mapListEntry, meal) -> {
                            LocalDate mealDate = meal.getDate();
                            DailyMealCaloriesCounter dailyMealCaloriesCounter =
                                    mapListEntry.getKey()
                                            .computeIfAbsent(mealDate,
                                                    d -> new DailyMealCaloriesCounter(caloriesPerDay, mealDate, 0));
                            dailyMealCaloriesCounter.addCalories(meal.getCalories());
                            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                                mapListEntry.getValue().add(getUserMealWithExcess(meal, dailyMealCaloriesCounter));
                            }
                        },
                        (s1, s2) -> {
                            throw new UnsupportedOperationException();
                        }
                )).getValue();
    }

    private static UserMealWithExcess getUserMealWithExcess(UserMeal meal, boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    private static UserMealWithExcess getUserMealWithExcess(UserMeal meal,
                                                            DailyMealCaloriesCounter dailyMealCaloriesCounter) {
        return new UserMealWithExcess(meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories(),
                dailyMealCaloriesCounter);
    }

}

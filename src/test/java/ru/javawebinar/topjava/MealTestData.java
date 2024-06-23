package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final Meal userMeal1 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак"
            , 500);
    public static final Meal userMeal2 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед",
                                                  1000);
    public static final Meal userMeal3 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин",
                                                  500);
    public static final Meal userMeal4 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на " +
            "граничное значение", 100);
    public static final Meal userMeal5 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак"
            , 1000);
    public static final Meal userMeal6 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед",
                                                  500);
    public static final Meal userMeal7 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин",
                                                  410);

    public static final Meal adminMeal1 = new Meal(100010, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                                                   "Завтрак ADMIN", 500);
    public static final Meal adminMeal2 = new Meal(100011, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед " +
            "ADMIN", 1000);
    public static final Meal adminMeal3 = new Meal(100012, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин " +
            "ADMIN", 500);
    public static final Meal adminMeal4 = new Meal(100013, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на " +
            "граничное значение ADMIN", 100);
    public static final Meal adminMeal5 = new Meal(100014, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                                                   "Завтрак ADMIN", 1000);
    public static final Meal adminMeal6 = new Meal(100015, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед " +
            "ADMIN", 500);
    public static final Meal adminMeal7 = new Meal(100016, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин " +
            "ADMIN", 410);

    public static List<Meal> getUserMeals() {
        return Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    public static List<Meal> getAdminMeals() {
        return Arrays.asList(adminMeal7, adminMeal6, adminMeal5, adminMeal4, adminMeal3, adminMeal2, adminMeal1);
    }

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.of(2024, Month.JUNE, 25, 10, 0), "New breakfast", 300);
    }

    public static Meal getMealUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.of(2024, Month.JANUARY, 30, 10, 0));
        updated.setDescription("Updated breakfast");
        updated.setCalories(600);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}

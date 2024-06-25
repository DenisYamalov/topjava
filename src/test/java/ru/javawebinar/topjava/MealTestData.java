package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


public class MealTestData {
    public static final Meal userMeal1 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на " + "граничное значение", 100);
    public static final Meal userMeal5 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final Meal adminMeal1 = new Meal(START_SEQ + 10, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак ADMIN", 500);
    public static final Meal adminMeal2 = new Meal(START_SEQ + 11, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед " + "ADMIN", 1000);
    public static final Meal adminMeal3 = new Meal(START_SEQ + 12, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин " + "ADMIN", 500);
    public static final Meal adminMeal4 = new Meal(START_SEQ + 13, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на " + "граничное значение ADMIN", 100);
    public static final Meal adminMeal5 = new Meal(START_SEQ + 14, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак ADMIN", 1000);
    public static final Meal adminMeal6 = new Meal(START_SEQ + 15, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед " + "ADMIN", 500);
    public static final Meal adminMeal7 = new Meal(START_SEQ + 16, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин " + "ADMIN", 410);

    public static final List<Meal> userMeals = Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);

    public static final List<Meal> adminMeals = Arrays.asList(adminMeal7, adminMeal6, adminMeal5, adminMeal4, adminMeal3, adminMeal2, adminMeal1);

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

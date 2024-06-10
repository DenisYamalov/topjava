package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal createOrUpdate(Meal meal);

    Meal getById(int id);

    void delete(int id);

    List<Meal> getAll();

}

package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryDao implements MealDao {
    private static final AtomicInteger counter = new AtomicInteger(1);

    private static int getId() {
        return counter.incrementAndGet();
    }

    private static final ConcurrentMap<Integer, Meal> meaMap = new ConcurrentHashMap<>();

    @Override
    public Meal createOrUpdate(Meal meal) {
        if (meal.getId() == 0) {
            int newId = getId();
            meal = new Meal(newId, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        }
        meaMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal getById(int id) {
        return meaMap.get(id);
    }

    @Override
    public void delete(int id) {
        meaMap.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meaMap.values());
    }

}

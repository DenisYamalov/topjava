package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealDao implements MealDao {
    private static final AtomicInteger counter = new AtomicInteger(0);

    private static final ConcurrentMap<Integer, Meal> meaMap = new ConcurrentHashMap<>();

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null || !meaMap.containsKey(meal.getId())) {
            int newId = getId();
            meal = new Meal(newId, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        }
        return meaMap.merge(meal.getId(), meal, (meal1, meal2) -> meal2);
    }

    private static int getId() {
        return counter.incrementAndGet();
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

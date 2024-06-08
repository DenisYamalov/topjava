package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MealMapDao implements MealDao {
    private static final ConcurrentMap<Integer, Meal> meaMap = new ConcurrentHashMap<>();

    @Override
    public void clear() {
        meaMap.clear();
    }

    @Override
    public void save(Meal meal) {
        meaMap.putIfAbsent(meal.getId(), meal);
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

    @Override
    public int size() {
        return meaMap.size();
    }

    @Override
    public void update(Meal meal) {
        meaMap.put(meal.getId(), meal);
    }

    @Override
    public boolean isExist(int id) {
        return meaMap.containsKey(id);
    }
}

package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        Meal oldMeal = repository.get(meal.getId());
        if (oldMeal != null) {
            if (oldMeal.getUserId().equals(userId)) {
                meal.setUserId(userId);
                repository.put(meal.getId(), meal);
                return meal;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId().equals(userId)) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId().equals(userId)) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return filterByPredicate(userId, meal ->
                DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDate.atStartOfDay(),
                                               endDate.atTime(LocalTime.MAX)));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> predicate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}


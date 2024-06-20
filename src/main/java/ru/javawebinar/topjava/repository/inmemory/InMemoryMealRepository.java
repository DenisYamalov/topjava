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
        Meal computed = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            if (isOwnerId(oldMeal, userId)) {
                meal.setUserId(userId);
                return meal;
            }
            return oldMeal;
        });
        return isOwnerId(computed, userId) ? computed : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        return isOwnerId(meal, userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return isOwnerId(meal, userId) ? meal : null;
    }

    private boolean isOwnerId(Meal meal, int userId) {
        return meal != null && meal.getUserId().equals(userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(meal -> true, userId);
    }

    @Override
    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, int userId) {
        return filterByPredicate(meal ->
                DateTimeUtil.isBetweenHalfOpen(meal.getDate().atStartOfDay(), startDate.atStartOfDay(),
                                               endDate.atTime(LocalTime.MAX)), userId);
    }

    private List<Meal> filterByPredicate(Predicate<Meal> predicate, int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}


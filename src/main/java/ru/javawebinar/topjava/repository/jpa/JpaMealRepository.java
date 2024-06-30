package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            int result = em.createNamedQuery(Meal.UPDATE)
                    .setParameter("description", meal.getDescription())
                    .setParameter("calories", meal.getCalories())
                    .setParameter("date_time", meal.getDateTime())
                    .setParameter("id", meal.id())
                    .setParameter("user_id", userId)
                    .executeUpdate();
            return result == 0 ? null : meal;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> resultList = em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getResultList();
        return DataAccessUtils.singleResult(resultList);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.All_SORTED, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.All_BY_DATES, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("start_date_time", startDateTime)
                .setParameter("end_date_time", endDateTime)
                .getResultList();
    }
}
package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.admin;
import static ru.javawebinar.topjava.UserTestData.user;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(userMeal1.getId(), user.getId());
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(userMeal1.getId(), admin.getId()));
    }

    @Test
    public void delete() {
        service.delete(userMeal1.getId(), user.getId());
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(userMeal1.getId(), admin.getId()));
    }

    @Test
    public void getBetweenInclusive() {
        //Meals list of 2020-01-30
        List<Meal> mealsBetweenDate = Arrays.asList(userMeal3, userMeal2, userMeal1);
        assertMatch(service.getBetweenInclusive(LocalDate.of(2020, 1, 30),
                                                LocalDate.of(2020, 1, 30), user.getId()), mealsBetweenDate);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(user.getId());
        List<Meal> allAdmin = service.getAll(admin.getId());
        assertMatch(all, getUserMeals());
        assertMatch(allAdmin, getAdminMeals());
    }

    @Test
    public void update() {
        Meal updated = getMealUpdated();
        service.update(updated, user.getId());
        assertMatch(service.get(updated.getId(), user.getId()), updated);
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(userMeal1, admin.getId()));
    }

    @Test
    public void create() {
        Meal created = service.create(getNewMeal(), user.getId());
        Integer createdId = created.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(createdId);
        assertMatch(created, newMeal);
        assertMatch(service.get(createdId, user.getId()), newMeal);
    }

    @Test
    public void duplicateMailCreate() {
        getDataAccessException(new Meal(userMeal1.getDateTime(),
                                        userMeal1.getDescription(),
                                        userMeal1.getCalories()), user.getId());
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplcateDateMeal = new Meal(userMeal1.getDateTime(), "Duplicate date-time meal", 100);
        getDataAccessException(duplcateDateMeal, user.getId());
    }

    private void getDataAccessException(Meal meal, int userId) {
        assertThrows(DataAccessException.class, () -> service.create(meal, userId));
    }
}
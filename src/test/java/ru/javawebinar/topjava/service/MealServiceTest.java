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
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/jdbc-repo.xml", "classpath:spring/spring-db.xml"})
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
        Meal meal = service.get(userMeal1.getId(), USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, () -> service.get(userMeal1.getId(), ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(AbstractBaseEntity.START_SEQ + 100, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(userMeal1.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(userMeal1.getId(), USER_ID));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, () -> service.delete(userMeal1.getId(), ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(AbstractBaseEntity.START_SEQ + 100, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        //Meals list of 2020-01-30
        List<Meal> mealsBetweenDate = Arrays.asList(userMeal3, userMeal2, userMeal1);
        assertMatch(service.getBetweenInclusive(LocalDate.of(2020, 1, 30),
                LocalDate.of(2020, 1, 30), USER_ID), mealsBetweenDate);
    }

    @Test
    public void getBetweenNull() {
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), userMeals);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        List<Meal> allAdmin = service.getAll(ADMIN_ID);
        assertMatch(all, userMeals);
        assertMatch(allAdmin, adminMeals);
    }

    @Test
    public void update() {
        Meal updated = getMealUpdated();
        service.update(updated, UserTestData.USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), getMealUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal meal = new Meal(userMeal1);
        assertThrows(NotFoundException.class, () -> service.update(meal, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNewMeal(), USER_ID);
        Integer createdId = created.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(createdId);
        assertMatch(created, newMeal);
        assertMatch(service.get(createdId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplcateDateMeal = new Meal(userMeal1.getDateTime(), "Duplicate date-time meal", 100);
        getDataAccessException(duplcateDateMeal, USER_ID);
    }

    private void getDataAccessException(Meal meal, int userId) {
        assertThrows(DataAccessException.class, () -> service.create(meal, userId));
    }
}
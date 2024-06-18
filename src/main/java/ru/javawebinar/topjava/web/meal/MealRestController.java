package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return getTos();
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getFiltered startDate: {}, startTime: {}, endDate: {}, endTime{}", startDate, startTime, endDate,
                 endTime);
        LocalTime startLocalTime = startTime == null ? LocalTime.MIDNIGHT : startTime;
        LocalTime endLocalTime = endTime == null ? LocalTime.MAX : endTime;
        if (startDate == null && endDate == null) {
            if (startTime == null && endTime == null) {
                return getTos();
            }
            return MealsUtil.getFilteredTos(service.getAll(SecurityUtil.authUserId()),
                                            SecurityUtil.authUserCaloriesPerDay(), startLocalTime, endLocalTime);
        }
        startDate = startDate == null ? LocalDate.MIN : startDate;
        endDate = endDate == null ? LocalDate.MAX : endDate;
        return MealsUtil.getFilteredTos(service.getFiltered(SecurityUtil.authUserId(), startDate, endDate),
                                        SecurityUtil.authUserCaloriesPerDay(), startLocalTime, endLocalTime);
    }

    private List<MealTo> getTos() {
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }
}
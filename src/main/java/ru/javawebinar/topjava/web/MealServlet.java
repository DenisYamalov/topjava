package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoDefault;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealIdCounter;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEALS = "/meals.jsp";
    private final MealDao mealDao;

    public MealServlet() {
        mealDao = MealDaoDefault.getInstance();
        //FOR TESTING ONLY
        MealsUtil.meals.forEach(mealDao::save);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        Meal meal = null;
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("meals", MealsUtil.mapToList(mealDao.getAll(), MealsUtil.CALORIES_PER_DAY));
            req.getRequestDispatcher(LIST_MEALS).forward(req, resp);
            return;
        }
        switch (action) {
            case "insert":
                meal = new Meal(MealIdCounter.getId(), LocalDateTime.now(), "", 0);
            case "edit":
                forward = INSERT_OR_EDIT;
                if (meal == null) {
                    int id = Integer.parseInt(req.getParameter("mealId"));
                    meal = mealDao.getById(id);
                }
                req.setAttribute("meal", meal);
                break;
            case "delete":
                int mealId = Integer.parseInt(req.getParameter("mealId"));
                mealDao.delete(mealId);
                forward = LIST_MEALS;
            case "listMeals":
                req.setAttribute("meals", MealsUtil.mapToList(mealDao.getAll(), MealsUtil.CALORIES_PER_DAY));
                break;
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime date = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(id, date, description, calories);
        if (mealDao.isExist(id)) {
            mealDao.update(meal);
        } else {
            mealDao.save(meal);
        }
        resp.sendRedirect("meals");
    }
}

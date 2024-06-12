package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MemoryMealDao;
import ru.javawebinar.topjava.model.Meal;
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
    private MealDao mealDao;

    @Override
    public void init() {
        mealDao = new MemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        String forward;
        Meal meal = null;
        switch (action) {
            case "insert":
                meal = new Meal(null, LocalDateTime.now(), "", 0);
                log.info("Inserting meal");
            case "edit":
                forward = INSERT_OR_EDIT;
                if (meal == null) {
                    int id = Integer.parseInt(req.getParameter("mealId"));
                    meal = mealDao.getById(id);
                }
                req.setAttribute("meal", meal);
                log.info("Editing meal: {}", meal);
                break;
            case "delete":
                int mealId = Integer.parseInt(req.getParameter("mealId"));
                mealDao.delete(mealId);
                log.info("Meal id = {} removed", mealId);
                resp.sendRedirect(req.getRequestURI());
                return;
            default:
                req.setAttribute("meals", MealsUtil.mapToList(mealDao.getAll(), MealsUtil.CALORIES_PER_DAY));
                req.getRequestDispatcher(LIST_MEALS).forward(req, resp);
                return;
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Integer id = null;
        String reqParameterId = req.getParameter("id");
        if (!reqParameterId.isEmpty()) {
            id = Integer.parseInt(reqParameterId);
            log.info("Updating meal id = {}", id);
        } else {
            log.info("New meal creating");
        }
        LocalDateTime date = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(id, date, description, calories);
        Meal savedMeal = mealDao.save(meal);
        log.info("Saved meal: {}", savedMeal);
        resp.sendRedirect("meals");
    }
}

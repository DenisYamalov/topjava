package ru.javawebinar.topjava.web.user.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AbstractAdminRestControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaAdminRestControllerTest extends AbstractAdminRestControllerTest {

    @Test
    void getWithMeals() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID + "/with-meals"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User adminWithMeals = USER_WITH_MEALS_MATCHER.readFromJson(action);
        USER_MATCHER.assertMatch(adminWithMeals, UserTestData.admin);
        MEAL_MATCHER.assertMatch(adminWithMeals.getMeals(), MealTestData.adminMeal2, MealTestData.adminMeal1);
    }
}

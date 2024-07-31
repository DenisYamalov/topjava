package ru.javawebinar.topjava.web.user.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AbstractProfileRestControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaProfileRestControllerTest extends AbstractProfileRestControllerTest {

    @Test
    void getWithMeals() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + "/with-meals"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User userWithMeals = USER_WITH_MEALS_MATCHER.readFromJson(action);
        USER_MATCHER.assertMatch(userWithMeals, user);
        MEAL_MATCHER.assertMatch(userWithMeals.getMeals(), MealTestData.meals);
    }
}

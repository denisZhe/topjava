package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.DATAJPA})
public class UserServiceDataJpaTest extends UserServiceTest {

    @Test
    public void testGetWithMeals() {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        List<Meal> meals = user.getMeals();
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        MealTestData.MATCHER.assertCollectionEquals(MEALS, meals);
    }

    @Test
    public void testGetWithNoMeals() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        User user = service.getWithMeals(created.getId());
        MATCHER.assertEquals(created, user);
        List<Meal> meals = user.getMeals();
        MealTestData.MATCHER.assertCollectionEquals(new ArrayList<>(), meals);
    }

    @Test
    public void testNotFoundGetWithMeals() {
        thrown.expect(NotFoundException.class);
        User user = service.getWithMeals(1);
    }
}

package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    private static final int USER_ID = START_SEQ;
    private static final int ADMIN_ID = START_SEQ + 1;

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.JULY, 14, 11, 0), "test meal", 850);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        List<Meal> meals = new ArrayList<>(MEALS_USER);
        meals.add(0, newMeal);
        MATCHER.assertCollectionEquals(meals, service.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal saved = service.save(MEAL, USER_ID);
        Meal updated = new Meal(MEAL);
        updated.setId(saved.getId());
        updated.setDescription("updated test meal");
        updated.setCalories(333);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(updated.getId(), USER_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(START_SEQ + 8, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEALS_ADMIN.get(0)), service.getAll(ADMIN_ID));
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(START_SEQ + 9, ADMIN_ID);
        MATCHER.assertEquals(MEALS_ADMIN.get(0), meal);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Meal> userMeals = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(MEALS_USER, userMeals);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        LocalDateTime startDate = MEALS_USER.get(3).getDateTime();
        LocalDateTime endDate = MEALS_USER.get(0).getDateTime();
        List<Meal> expectedMeals = Arrays.asList(MEALS_USER.get(0), MEALS_USER.get(1), MEALS_USER.get(2), MEALS_USER.get(3));
        MATCHER.assertCollectionEquals(expectedMeals, service.getBetweenDateTimes(startDate, endDate, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDeleteForIncorrectUser() throws Exception {
        service.delete(START_SEQ + 7, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFoundForIncorrectUser() throws Exception {
        service.get(START_SEQ + 7, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Meal updated = new Meal(MEAL);
        updated.setId(1);
        service.update(updated, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFoundForIncorrectUser() throws Exception {
        service.update(MEALS_USER.get(0), ADMIN_ID);
    }
}
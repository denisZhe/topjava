package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

    public static final Meal MEAL =
            new Meal(LocalDateTime.of(2017, 4, 12, 21, 0), "test meal", 555);

    public static final List<Meal> MEALS_USER = new ArrayList<>(Arrays.asList(
            new Meal(START_SEQ + 7, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(START_SEQ + 6, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
            new Meal(START_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
            new Meal(START_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(START_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(START_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)
    ));

    public static final List<Meal> MEALS_ADMIN = new ArrayList<>(Arrays.asList(
            new Meal(START_SEQ + 9, LocalDateTime.of(2015, 6, 1, 21, 0), "Админ ужин", 510),
            new Meal(START_SEQ + 8, LocalDateTime.of(2015, 6, 1, 14, 0), "Админ ланч", 510)
    ));

}
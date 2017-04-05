package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    Meal save(Meal meal);

    Meal get(int id, int userId) throws NotFoundException;

    List<MealWithExceed> getAll(int userId);

    List<MealWithExceed> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    void update(Meal meal, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;
}
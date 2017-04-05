package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id, userId);
        if (meal != null) {
            return meal;
        } else {
            throw new NotFoundException("Meal already exist or parameter equal null");
        }
    }

    @Override
    public List<MealWithExceed> getAll(int userId) {
        return MealsUtil.getWithExceeded(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public List<MealWithExceed> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LocalDate sd = (startDate == null) ? LocalDate.MIN : startDate;
        LocalDate ed = (endDate == null) ? LocalDate.MAX : endDate;
        LocalTime st = (startTime == null) ? LocalTime.MIN : startTime;
        LocalTime et = (endTime == null) ? LocalTime.MAX : endTime;

        List<Meal> meals = repository.getFiltered(userId, sd, ed);
        return MealsUtil.getFilteredWithExceeded(meals, st, et, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        if (!repository.update(meal, userId)) {
            throw new NotFoundException("Meal not new or not found or parameter equal null or incorrect user ID");
        }
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (!repository.delete(id, userId)) {
            throw new NotFoundException("Meal not new or not found or parameter equal null or incorrect user ID");
        }
    }
}
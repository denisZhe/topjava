package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.HardCodedMealDaoImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealServiceImpl implements MealService {
    private MealDao mealDao = new HardCodedMealDaoImpl();

    @Override
    public void addMeal(Meal meal) {
        mealDao.addMeal(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealDao.updateMeal(meal);
    }

    @Override
    public void removeMeal(long id) {
        mealDao.removeMeal(id);
    }

    @Override
    public Meal getMealById(long id) {
        return mealDao.getMealById(id);
    }

    @Override
    public List<MealWithExceed> getAllExceededMeal(int caloriesPerDay) {
        List<Meal> meals = mealDao.getAllMeal();
        return MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }
}
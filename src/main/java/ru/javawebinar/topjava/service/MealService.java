package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealService {
    public void addMeal(Meal meal);

    public void updateMeal(Meal meal);

    public void removeMeal(long id);

    public Meal getMealById(long id);

    public List<MealWithExceed> getAllExceededMeal(int caloriesPerDay);
}
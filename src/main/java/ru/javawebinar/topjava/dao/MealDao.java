package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    public void addMeal(Meal meal);

    public void updateMeal(Meal meal);

    public void removeMeal(long id);

    public Meal getMealById(long id);

    public List<Meal> getAllMeal();
}
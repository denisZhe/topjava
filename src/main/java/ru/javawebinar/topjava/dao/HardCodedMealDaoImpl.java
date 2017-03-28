package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class HardCodedMealDaoImpl implements MealDao {
    private static final Logger LOG = getLogger(HardCodedMealDaoImpl.class);

    private static final ConcurrentMap<Long, Meal> meals = new ConcurrentHashMap<>();
    private static final AtomicLong mealId = new AtomicLong(1);

    static {
        meals.put(mealId.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.put(mealId.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.put(mealId.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.put(mealId.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.put(mealId.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.put(mealId.getAndIncrement(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

        meals.forEach((id, meal) -> meal.setId(id));

        LOG.debug("Hardcoded meals DB initialized");
    }

    @Override
    public void addMeal(Meal meal) {
        if (meal.getId() > 0) {
            LOG.debug("meal {} already exist", meal.getDescription());
        } else {
            long id = mealId.getAndIncrement();
            meals.put(id, meal);
            meal.setId(id);
            LOG.debug("meal {} created", meal.getDescription());
        }
    }

    @Override
    public void updateMeal(Meal meal) {
        Meal oldMeal = meals.put(meal.getId(), meal);
        if (oldMeal != null) {
            LOG.debug("meal {} updated", meal.getDescription());
        } else {
            LOG.debug("meal with ID = {} didn't exist and was created", meal.getId());
        }
    }

    @Override
    public void removeMeal(long id) {
        Meal meal = meals.remove(id);
        if (meal != null) {
            LOG.debug(meal.getDescription() + " removed");
        } else {
            LOG.debug("meal with ID = {} doesn't exist and could not be removed", id);
        }
    }

    @Override
    public Meal getMealById(long id) {
        Meal meal = meals.get(id);
        if (meal != null) {
            LOG.debug("get meal " + meal.getDescription());
        } else {
            LOG.debug("meal with ID = {} doesn't exist", id);
        }
        return meal;
    }

    @Override
    public List<Meal> getAllMeal() {
        List<Meal> mealsList = meals.values().stream().collect(Collectors.toList());
        LOG.debug("read all meals");
        return mealsList;
    }
}
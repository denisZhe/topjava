package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal getByIdAndUserId(int id, int userId);

    List<Meal> getAllByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> getAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);

    @Override
    @Transactional
    Meal save(Meal meal);
}

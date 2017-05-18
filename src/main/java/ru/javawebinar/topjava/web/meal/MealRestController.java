package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.formatter.CustomDateTimeFormat;
import ru.javawebinar.topjava.util.formatter.CustomDateTimeFormatter;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal created = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @Override
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestParam(value = "startDate", required = false)
                                           @CustomDateTimeFormat(style = CustomDateTimeFormatter.Style.DATE) LocalDate startDate,
                                           @RequestParam(value = "startTime", required = false)
                                           @CustomDateTimeFormat(style = CustomDateTimeFormatter.Style.TIME) LocalTime startTime,
                                           @RequestParam(value = "endDate", required = false)
                                           @CustomDateTimeFormat(style = CustomDateTimeFormatter.Style.DATE) LocalDate endDate,
                                           @RequestParam(value = "endTime", required = false)
                                           @CustomDateTimeFormat(style = CustomDateTimeFormatter.Style.TIME) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @GetMapping(value = "/simple-filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getSimpleBetween(@RequestParam(value = "start", required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                 @RequestParam(value = "end", required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        LocalDateTime startDateTime = LocalDateTime.of(MIN_DATE, LocalTime.MIN);
        if (start != null) {
            startDateTime = start;
        }
        LocalDateTime endDateTime = LocalDateTime.of(MAX_DATE, LocalTime.MAX);
        if (end != null) {
            endDateTime = end;
        }
        return super.getBetween(startDateTime.toLocalDate(), startDateTime.toLocalTime(),
                endDateTime.toLocalDate(), endDateTime.toLocalTime());
    }
}
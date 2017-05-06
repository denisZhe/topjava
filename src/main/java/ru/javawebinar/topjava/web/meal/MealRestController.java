package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping(value = "/meals")
public class MealRestController extends AbstractMealController {

    public MealRestController(MealService service) {
        super(service);
    }

    @RequestMapping(value = "/delete")
    public String removeUser(@RequestParam(value = "id") int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping()
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(value = "/create")
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/update")
    public String update(Model model, @RequestParam int id) {
        final Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam String id,
                       @RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam String calories) {

        final Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.valueOf(calories));

        if (id.isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, Integer.valueOf(id));
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(@RequestParam String startDate,
                             @RequestParam String startTime,
                             @RequestParam String endDate,
                             @RequestParam String endTime,
                             Model model) {

        LocalDate sd = DateTimeUtil.parseLocalDate(startDate);
        LocalDate ed = DateTimeUtil.parseLocalDate(endDate);
        LocalTime st = DateTimeUtil.parseLocalTime(startTime);
        LocalTime et = DateTimeUtil.parseLocalTime(endTime);
        model.addAttribute("meals", super.getBetween(sd, st, ed, et));
        return "meals";
    }
}
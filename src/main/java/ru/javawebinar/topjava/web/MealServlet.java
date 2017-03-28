package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private final MealService mealService = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("remove_id") != null) {
            remove(req, resp);
        } else if (req.getRequestURI().contains("edit")) {
            edit(req, resp);
        } else if ("addMeal".equals(req.getParameter("action"))) {
            LOG.debug("GET edit.jsp for add meal");
            req.setAttribute("action", "addMeal");
            req.getRequestDispatcher("/edit.jsp").forward(req, resp);

        } else {
            LOG.debug("GET meals.jsp");
            List<MealWithExceed> mealWithExceedList = mealService.getAllExceededMeal(2000);
            req.setAttribute("mealWithExceedList", mealWithExceedList);

            LOG.debug("send meals.jsp");
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = getMealFromRequest(req);
        if (meal != null) {
            String strId = req.getParameter("id");
            if (strId.isEmpty()) {
                mealService.addMeal(meal);
                resp.sendRedirect("meals");
            } else if (isCorrectId(strId)) {
                meal.setId(Long.parseLong(strId));
                mealService.updateMeal(meal);
                resp.sendRedirect("meals");
            }
        } else {
            LOG.debug("incorrect meal data from post request");
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("GET to remove meal with ID = {} ", req.getParameter("remove_id"));
        String strId = req.getParameter("remove_id");
        if (isCorrectId(strId)) {
            mealService.removeMeal(Long.parseLong(strId));
            resp.sendRedirect("meals");
        } else {
            LOG.debug("incorrect id from request for remove, id must be a positive number");
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOG.debug("GET edit.jsp for edit meal");
        String strId = req.getParameter("edit_id");
        if (isCorrectId(strId)) {
            Meal meal = mealService.getMealById(Long.parseLong(strId));
            if (meal != null) {
                req.setAttribute("editedMeal", meal);
                LOG.debug("send edit.jsp");
                req.getRequestDispatcher("/edit.jsp").forward(req, resp);
            } else {
                LOG.debug("meal doesn't exist,redirect to meals.jsp");
                resp.sendRedirect("meals");
            }
        } else {
            LOG.debug("incorrect id from request for update, id must be a positive number");
            resp.sendRedirect("meals");
        }
    }

    private boolean isCorrectId(String strId) {
        try {
            long id = Long.parseLong(strId);
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Meal getMealFromRequest(HttpServletRequest req) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            LOG.debug("meal created from request parameters");
            return new Meal(dateTime, description, calories);
        } catch (DateTimeParseException e) {
            LOG.debug("incorrect format of date and time from post request");
        } catch (NumberFormatException e) {
            LOG.debug("incorrect calories from post request");
        }
        return null;
    }
}
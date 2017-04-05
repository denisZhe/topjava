package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
public class MealRestController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public void resolve(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getMethod().equalsIgnoreCase("get")) {
            String action = request.getParameter("action");

            switch (action == null ? "all" : action) {
                case "delete":
                    delete(request, response);
                    break;
                case "create":
                    create(request, response);
                    break;
                case "update":
                    update(request, response);
                    break;
                case "all":
                default:
                    getAll(request, response);
                    break;
            }
        } else if (request.getMethod().equalsIgnoreCase("post")) {
            request.setCharacterEncoding("UTF-8");

            String action = request.getParameter("action");
            if (action != null && !action.isEmpty()) {
                getFiltered(request, response);
            } else {
                save(request, response);
            }
        }
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("getAll");
        request.setAttribute("meals", service.getAll(AuthorizedUser.id()));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    public void getFiltered(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("getFiltered");

        LocalDate startDate = (!StringUtils.isEmpty(request.getParameter("startDate"))) ? LocalDate.parse(request.getParameter("startDate")) : null;
        request.setAttribute("startDate", startDate);

        LocalDate endDate = (!StringUtils.isEmpty(request.getParameter("endDate"))) ? LocalDate.parse(request.getParameter("endDate")) : null;
        request.setAttribute("endDate", endDate);

        LocalTime startTime = (!StringUtils.isEmpty(request.getParameter("startTime"))) ? LocalTime.parse(request.getParameter("startTime")) : null;
        request.setAttribute("startTime", startTime);

        LocalTime endTime = (!StringUtils.isEmpty(request.getParameter("endTime"))) ? LocalTime.parse(request.getParameter("endTime")) : null;
        request.setAttribute("endTime", endTime);

        request.setAttribute("meals", service.getFiltered(AuthorizedUser.id(), startDate, endDate, startTime, endTime));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, AuthorizedUser.id());
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/meal.jsp").forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, ServletException, IOException {
        final Meal meal = service.get(getId(request), AuthorizedUser.id());
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/meal.jsp").forward(request, response);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, IOException {
        int id = getId(request);
        LOG.info("Delete {}", id);
        service.delete(id, AuthorizedUser.id());
        response.sendRedirect("meals");
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                AuthorizedUser.id());

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        service.save(meal);
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
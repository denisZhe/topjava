package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.validation.Valid;

@Controller
public class RootController extends AbstractUserController {

    @Autowired
    public RootController(UserService service) {
        super(service);
    }

    @GetMapping("/")
    public String root() {
        return "redirect:meals";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/meals")
    public String meals() {
        return "meals";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        if (isError(userTo, result)) {
            return "profile";
        }
        super.update(userTo, AuthorizedUser.id());
        AuthorizedUser.get().update(userTo);
        status.setComplete();
        return "redirect:meals";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (isError(userTo, result)) {
            model.addAttribute("register", true);
            return "profile";
        }
        super.create(UserUtil.createNewFromTo(userTo));
        status.setComplete();
        return "redirect:login?message=app.registered&username=" + userTo.getEmail();
    }

    private boolean isError(UserTo userTo, BindingResult result) {
        boolean isError = false;
        if (result.hasErrors()) {
            isError = true;
        }
        try {
            User user = service.getByEmail(userTo.getEmail());
            if (AuthorizedUser.safeGet() != null) {
                if (user.getId() != AuthorizedUser.id()) {
                    isError = true;
                    result.rejectValue("email", "users.duplicateEmail");
                }
            } else {
                if (userTo.getEmail().equals(user.getEmail())) {
                    isError = true;
                    result.rejectValue("email", "users.duplicateEmail");
                }
            }
        } catch (NotFoundException e) {
            return isError;
        }
        return isError;
    }
}

package home.marco.booksapp.controller;

import home.marco.booksapp.controller.dto.UserRegistrationDto;
import home.marco.booksapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/registration")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/new_user")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        userService.saveUser(registrationDto);

        return "redirect:/registration/showRegistrationForm?success";
    }

    @GetMapping(value = "/showRegistrationForm")
    public String showRegistrationForm(Model model) {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        model.addAttribute("user", registrationDto);
        return "registration";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
}

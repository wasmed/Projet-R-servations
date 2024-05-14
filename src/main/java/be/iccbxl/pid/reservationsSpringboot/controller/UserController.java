package be.iccbxl.pid.reservationsSpringboot.controller;

import be.iccbxl.pid.reservationsSpringboot.model.User;
import be.iccbxl.pid.reservationsSpringboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;


    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") User user, Model model) {

        return "user/register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.user", "Passwords do not match");
        }

        // Vérifier si le login existe déjà
        if (userService.findByLogin(user.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.user", "Login already exists.");
            return "user/register";
        }

        // Vérifier si l'email existe déjà
        if (userService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.user", "Email already exists.");
            return "user/register";
        }
        if (bindingResult.hasErrors()) {
            return "user/register";
        } else {
            user.setRole("member");
            userService.addUser(user);
            userService.sendConfirmationEmail(user);
            model.addAttribute("message", "Registered Successfully! Please check your email for further instructions.");
            return "user/registrationConfirmation";
        }


    }
    @GetMapping("/registrationConfirmation")
    public String registrationConfirmation(Model model) {

        return "user/registrationConfirmation";
    }
    @GetMapping("/login")
    public String login(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication != null && authentication.isAuthenticated();
        model.addAttribute("loggedIn", loggedIn);
        return "user/login";
    }


   /* @GetMapping("user-page")
    public String userPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user/user";
    }

    @GetMapping("admin-page")
    public String adminPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user/admin";
    }*/

}

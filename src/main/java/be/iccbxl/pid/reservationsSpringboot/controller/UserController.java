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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


        return "user/login";
    }


    @GetMapping("/member")
    public String profilePage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByLogin(username);

        model.addAttribute("user", user);
        model.addAttribute("listMembres", userService.getAllUsers());
        return "user/member";
    }


    @PostMapping("/member/saveUpdate")
    public String updateProfile(@ModelAttribute("user") User user, BindingResult bindingResult, Principal principal, Model model) {

        // Check for validation errors
        String username = principal.getName();
        User userprincipale = userService.findByLogin(username);

        User existingUserLogin = userService.findByLogin(user.getLogin());
        User existingUserEmail = userService.findByEmail(user.getEmail());

        if (existingUserLogin != null && existingUserLogin.getId() != userprincipale.getId()) {
            bindingResult.rejectValue("login", "error.user", "Login already exists.");
        }

        if (existingUserEmail != null && existingUserEmail.getId() != userprincipale.getId()) {
            bindingResult.rejectValue("email", "error.user", "Email already exists.");
        }

        if (bindingResult.hasErrors()) {

            return "user/member";
        } else {
            userService.updateUser(userprincipale.getId(), user);
            userService.sendConfirmationEmail(user);
            model.addAttribute("message", "Registered Successfully! Please check your email for further instructions.");
            return "user/updateConfirmation";
        }


    }


    @GetMapping("/password")
    public String getUpdatePasswordPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByLogin(username);
        model.addAttribute("user", user);
        model.addAttribute("listMembres", userService.getAllUsers());
        return "user/updatePassword";
    }

    @PostMapping("/password/update")
    public String updatePassword(@ModelAttribute("user") User user, BindingResult result, Principal principal, Model model) {
        // Vérifier si le nouveau mot de passe correspond à la confirmation du mot de passe
        String username = principal.getName();
        User userprincipale = userService.findByLogin(username);

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match");
            return "user/updatePassword";
        }

        if (result.hasErrors()) {
            return "user/updatePassword";
        } else {

            userService.updateUserPassword(userprincipale.getId(), user);
            // userService.sendConfirmationEmail(user);
            model.addAttribute("message", "Registered Successfully! Please check your email for further instructions.");
            return "user/updateConfirmation";

        }
    }
    @GetMapping("/passwordResetEmailSent")
    public String PasswordResetEmailSent(Model model) {

        return "user/passwordResetEmailSent";
    }

    @GetMapping("/forgot-password")
    public String ForgetPasswordPage(Model model) {

        return "user/forgotPassword";
    }

    @PostMapping("/forgot-password/sendEmail")
    public String processForgotPasswordForm(@ModelAttribute("email") String email,@ModelAttribute("password") String password, @ModelAttribute("confirmPassword") String confirmPassword,BindingResult result, Model model) {

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("errorMessage", "No user found with this email.");
            return "user/forgotPassword";
        }
        System.out.println("mot de passe :"+ password +" **"+ confirmPassword);
            if (!password.equals(confirmPassword) ){
                model.addAttribute("errorMessage", "Passwords do not match.");
                return "user/forgotpassword";
            }
        System.out.println("entrer pour update et send email");
                userService.updateUserPassword(user.getId(), user);
                userService.sendConfirmationEmailForgetPassword(user);
                model.addAttribute("message", "Registered Successfully! Please check your email for further instructions.");
                return "user/passwordResetEmailSent";

            }




}
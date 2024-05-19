package be.iccbxl.pid.reservationsSpringboot.controller;


import be.iccbxl.pid.reservationsSpringboot.model.User;
import be.iccbxl.pid.reservationsSpringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    UserService userService;

    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) {
       // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication != null && authentication.isAuthenticated();
        model.addAttribute("loggedIn", loggedIn);
        if (loggedIn) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userService.findRoleByUsername(userDetails.getUsername());
            model.addAttribute("role", role);
        }
    }


}

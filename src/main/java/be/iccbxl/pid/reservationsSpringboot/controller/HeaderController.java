package be.iccbxl.pid.reservationsSpringboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeaderController {

    @GetMapping("/header")
    public String header(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication != null && authentication.isAuthenticated();
        model.addAttribute("loggedIn", loggedIn);
        return "fragments/header";
    }

}

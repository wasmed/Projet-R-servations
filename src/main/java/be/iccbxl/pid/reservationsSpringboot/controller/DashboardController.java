package be.iccbxl.pid.reservationsSpringboot.controller;

import be.iccbxl.pid.reservationsSpringboot.model.Artist;
import be.iccbxl.pid.reservationsSpringboot.model.Location;
import be.iccbxl.pid.reservationsSpringboot.model.Show;
import be.iccbxl.pid.reservationsSpringboot.model.User;
import be.iccbxl.pid.reservationsSpringboot.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ShowService showService;

    @Autowired
    private UserService userService;


    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        long userCount = userService.countUsers();
        long artistCount = artistService.countArtists();
        long showCount = showService.countShows();
        List<User> users = userService.getAllUsers();
        int memberCount = userService.countMembers(); // Count member users
        int adminCount = userService.countAdmins(); // Count admin users
        List<Show> shows = showService.getAll();

        model.addAttribute("locations", locationService.getAll()); // Récupérer les locations
        model.addAttribute("types", typeService.getAll());          // Récupérer les types
        model.addAttribute("userCount", userCount);
        model.addAttribute("user", userDetails);
        model.addAttribute("users", users);
        model.addAttribute("memberCount", memberCount);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("artistCount", artistCount);
        model.addAttribute("showsCount", showCount);
        model.addAttribute("shows", shows);
        if (model.containsAttribute("userToEdit")) {
            model.addAttribute("userToEdit", model.getAttribute("userToEdit"));
        }
        if (model.containsAttribute("showToEdit")) {
            model.addAttribute("showToEdit", model.getAttribute("showToEdit"));
        }
        if (model.containsAttribute("locationToEdit")) {
            model.addAttribute("locationToEdit", model.getAttribute("locationToEdit"));
        }
        if (model.containsAttribute("typeToEdit")) {
            model.addAttribute("typeToEdit", model.getAttribute("typeToEdit"));
        }
        return "user/admin";
    }
    // Méthodes pour l'ajout (POST)
    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newUser", bindingResult);
            redirectAttributes.addFlashAttribute("newUser", user);
            return "redirect:/admin"; // Rediriger vers la page admin avec les erreurs
        }

        userService.addUser(user);
        return "redirect:/admin";
    }

    // ... (mêmes méthodes POST pour addShow, addLocation, addType)

    // Méthodes pour la modification (GET et POST)
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(id);
        redirectAttributes.addFlashAttribute("userToEdit", user);
        return "redirect:/admin";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("userToEdit") @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userToEdit", bindingResult);
            redirectAttributes.addFlashAttribute("userToEdit", user);
            return "redirect:/admin";
        }

        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    // ... (mêmes méthodes GET/POST pour edit/update pour show, location, type)

    // Méthodes pour la suppression (GET)
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}

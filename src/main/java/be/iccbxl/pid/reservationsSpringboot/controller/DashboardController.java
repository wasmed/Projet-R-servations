package be.iccbxl.pid.reservationsSpringboot.controller;

import be.iccbxl.pid.reservationsSpringboot.model.*;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private ArtistService artistService;


    @Autowired
    private RepresentationService representationService;

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


    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("isAdminEditing", true); // Indicate admin editing mode
        return "user/member"; // Reuse your existing template
    }

    @PostMapping("/admin/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("userToEdit")  User user, BindingResult bindingResult , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isAdminEditing", true); // Retain admin editing mode
            return "user/member"; // Return to form with errors
        }
        userService.updateUserAsmin(id, user);
        return "redirect:/admin"; // Redirect
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }



    @GetMapping("/admin/shows/add")
    public String showAddForm(Model model) {
        model.addAttribute("show", new Show());
        model.addAttribute("allArtists", artistService.getAllArtists());
        model.addAttribute("allLocations", locationService.getAll());


        return "show/showAdd";
    }
    @PostMapping("/admin/shows/add")
    public String addShow(@ModelAttribute("show") Show show,
                           Model model) {

        showService.add(show);
        return "redirect:/admin";
    }

    @GetMapping("/admin/shows/assign-location/{showId}")
    public String assignLocationForm( Model model) {
        model.addAttribute("allShows", showService.getAll()); // Add all shows to the model
        model.addAttribute("allLocations", locationService.getAll());

        return "location/assignLocation"; // Or your existing "user/admin" template
    }
    @PostMapping("/admin/shows/assign-location/{showId}")
    public String assignLocation(@PathVariable Long showId,
                                 @RequestParam("locationId") Long locationId) {
        Show show = showService.get(showId);
        Location location = locationService.get(locationId.toString());

        if (show != null && location != null) {
            show.setLocation(location); // Associate the location
            showService.update(showId, show); // Save the updated show
        } else {
            // Handle the case where the show or location is not found (e.g., add an error message)
        }

        return "redirect:/admin"; // Redirect back to the show list
    }

    @PostMapping("/admin/shows/update/{id}")
    public String updateShow(@PathVariable Long id, @ModelAttribute("show") Show show, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allArtists", artistService.getAllArtists());
            model.addAttribute("allLocations", locationService.getAll());
          //  model.addAttribute("artistTypes", show.getCollaborateurs().keySet());
            return "show/showEdit";
        }

        // Handle Artist Associations (similar to addShow)


        showService.update(id, show);
        return "redirect:/admin/shows";
    }




    @PostMapping("/admin/shows/delete/{id}")
    public String deleteShow(@PathVariable Long id) {
        showService.delete(id);
        return "redirect:/admin/shows";
    }



}

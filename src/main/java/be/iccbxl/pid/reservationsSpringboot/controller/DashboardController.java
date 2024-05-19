package be.iccbxl.pid.reservationsSpringboot.controller;

import be.iccbxl.pid.reservationsSpringboot.model.User;
import be.iccbxl.pid.reservationsSpringboot.service.ArtistService;
import be.iccbxl.pid.reservationsSpringboot.service.ShowService;
import be.iccbxl.pid.reservationsSpringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private ArtistService artistService;
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
        model.addAttribute("userCount", userCount);
        model.addAttribute("user", userDetails);
        model.addAttribute("users", users);
        model.addAttribute("memberCount", memberCount);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("artistCount", artistCount);
        model.addAttribute("showsCount", showCount);

        return "user/admin";
    }
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/list"; // Replace with your actual user list template name
    }

    @PostMapping("/admin/users/update/{userId}")
    public ResponseEntity<User> updateMemberAdmin(@PathVariable Long userId, @RequestBody User updatedUser) {
        userService.updateUser(userId, updatedUser);
        User user = userService.getUser(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user/admin"; // Replace with your actual user edit template name
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        userService.updateUser(id,user);
        return "user/admin"; // Redirect to user list after update
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "user/admin"; // Redirect to user list after delete
    }

}

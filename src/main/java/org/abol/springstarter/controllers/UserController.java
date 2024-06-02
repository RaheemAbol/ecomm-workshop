package org.abol.springstarter.controllers;

import org.abol.springstarter.models.BaseUser;
import org.abol.springstarter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        List<BaseUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "all-users";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new BaseUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") BaseUser user) {
        userService.saveUser(user);
        return "redirect:/users/login"; // Redirect to login after registration
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        BaseUser user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, @ModelAttribute("user") BaseUser user) {
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/users/all";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users/all";
    }
}
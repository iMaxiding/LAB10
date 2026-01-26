package com.example.project1.controller;

// WAŻNE: Sprawdź czy masz dokładnie ten import:
import com.example.project1.model.User;
// NIE MOŻE BYĆ: import org.springframework.security.core.userdetails.User;

import com.example.project1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    private final UserService userService;

    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        // Tutaj tworzymy pusty obiekt Twojego modelu User
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Tutaj przekazujemy go do serwisu.
        // Jeśli importy się zgadzają, czerwony błąd zniknie.
        userService.createUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
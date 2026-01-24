package com.example.project1.controller;

import com.example.project1.dto.CreateUserRequest;
import com.example.project1.dto.LoginRequest;
import com.example.project1.model.User;
import com.example.project1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest req) {
        User created = userService.createUser(req);
        return ResponseEntity.ok(new PublicUser(created.getId(), created.getUsername(), created.getEmail()));
        // nie zwracamy hasła
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userService.authenticate(req);
        return ResponseEntity.ok(new PublicUser(user.getId(), user.getUsername(), user.getEmail()));
    }

    public record PublicUser(Long id, String username, String email) {}
}


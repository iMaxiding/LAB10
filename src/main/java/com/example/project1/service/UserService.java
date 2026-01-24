package com.example.project1.service;

import com.example.project1.dto.CreateUserRequest;
import com.example.project1.dto.LoginRequest;
import com.example.project1.model.User;
import com.example.project1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest req) {
        if (req.getUsername() == null || req.getUsername().isBlank())
            throw new IllegalArgumentException("Username is required");
        if (req.getEmail() == null || req.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");
        if (req.getPassword() == null || req.getPassword().isBlank())
            throw new IllegalArgumentException("Password is required");

        if (userRepository.existsByEmail(req.getEmail()))
            throw new IllegalStateException("Email already used");

        User user = new User(req.getUsername(), req.getEmail(), req.getPassword());
        return userRepository.save(user);
    }

    public User authenticate(LoginRequest req) {
        if (req.getEmail() == null || req.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");
        if (req.getPassword() == null || req.getPassword().isBlank())
            throw new IllegalArgumentException("Password is required");

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.getPassword().equals(req.getPassword()))
            throw new IllegalArgumentException("Invalid credentials");

        return user;
    }
}

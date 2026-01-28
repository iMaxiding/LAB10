package com.example.project1.security;

import com.example.project1.model.User;
import com.example.project1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    private final UserRepository userRepository;

    public CurrentUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long requireUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
            throw new IllegalStateException("Not authenticated");
        }

        String principal = auth.getName();

        User user = userRepository.findByEmail(principal)
                .orElseThrow(() -> new IllegalStateException("User not found: " + principal));

        return user.getId();
    }
}


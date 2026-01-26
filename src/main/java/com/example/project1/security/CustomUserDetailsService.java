package com.example.project1.security;

import com.example.project1.model.User;
import com.example.project1.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Szukamy użytkownika w bazie po emailu
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika: " + email));

        // Zwracamy obiekt, który rozumie Spring Security
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail()) // Naszym loginem jest email
                .password(user.getPassword())
                .roles("USER") // Każdy dostaje rolę USER (wymóg z PDF o rolach)
                .build();
    }
}
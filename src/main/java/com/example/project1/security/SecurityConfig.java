package com.example.project1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // WAŻNE: Tutaj wymieniamy adresy, które mają być dostępne BEZ logowania
                        .requestMatchers("/login", "/register", "/css/**", "/error").permitAll()
                        .anyRequest().authenticated() // Reszta wymaga logowania
                )
                .formLogin(form -> form
                        .loginPage("/login") // Wskazujemy nasz adres
                        .defaultSuccessUrl("/home", true) // Przekierowanie po sukcesie
                        .permitAll() // <--- KLUCZOWE: Pozwala każdemu wejść na formularz logowania!
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll() // To też musi być publiczne
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
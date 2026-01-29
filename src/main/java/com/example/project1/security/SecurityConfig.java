package com.example.project1.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Logger do zadań audytowych (Zadanie 3)
    private static final Logger auditLogger = LoggerFactory.getLogger("SECURITY_AUDIT");

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
                        .defaultSuccessUrl("/home", false) // Przekierowanie po sukcesie
                        .permitAll()
                        // ZADANIE 3: Logowanie nieudanych prób (SLF4J)
                        .failureHandler((request, response, exception) -> {
                            String username = request.getParameter("username");
                            String ipAddress = request.getRemoteAddr();

                            // Logujemy tylko bezpieczne dane (BEZ HASŁA!)
                            auditLogger.warn("NIEUDANA PRÓBA LOGOWANIA | User: {} | IP: {} | Błąd: {}",
                                    username, ipAddress, exception.getMessage());

                            // Po zalogowaniu błędu, przekieruj normalnie do login?error
                            response.sendRedirect("/login?error");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true) // ZADANIE 1.1: niszczenie sesji na serwerze
                        .deleteCookies("JSESSIONID") // ZADANIE 1.1: kasuje ciasteczko w przeglądarce
                        .permitAll()
                )
                // ZADANIE 2: NAGŁÓWKI BEZPIECZEŃSTWA
                .headers(headers -> headers
                        .contentTypeOptions(config -> {})       // 2.1 X-Content-Type-Options: nosniff
                        .frameOptions(config -> config.deny())  // 2.2 X-Frame-Options

                        // 2.3 Content-Security-Policy
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; object-src 'none'; img-src 'self' data:;")
                        )

                        // 2.4 Referrer-Policy
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )

                        // --- ZADANIE 5.3: HSTS (Strict-Transport-Security)
                        // To wymusza na przeglądarce korzystanie tylko z HTTPS
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .preload(true)
                                .maxAgeInSeconds(31536000) // Pamiętaj o tym przez rok
                        )
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
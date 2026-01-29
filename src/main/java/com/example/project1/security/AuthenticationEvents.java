package com.example.project1.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEvents.class);

    // --- PUNKT 3.1: Logowanie nieudanych prób logowania (Błąd 401) ---
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        String username = event.getAuthentication().getName();
        String error = event.getException().getMessage();

        // ZMIANA: Dopisałem "(401)", żeby zgadzało się idealnie z instrukcją
        logger.warn("LOGOWANIE BŁĄD (401): Nieudana próba dla użytkownika [{}]. Powód: {}", username, error);
    }

    // --- PUNKT 3.1: Logowanie braku dostępu (Błąd 403) ---
    @EventListener
    public void onAuthorizationFailure(AuthorizationDeniedEvent<?> event) {
        // Tu już mieliśmy 403, więc jest OK
        logger.warn("BRAK DOSTĘPU (403): Wykryto próbę nieautoryzowanego dostępu.");
    }
}
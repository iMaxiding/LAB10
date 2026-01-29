package com.example.project1.controller;

import com.example.project1.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Używamy MockitoBean dla Spring Boot 3.4
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

// Importy potrzebne do symulowania żądań i sprawdzania wyników
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Atrapa serwisu, żeby nie łączyć się z prawdziwą bazą danych
    private NoteService noteService;

    // --- ZADANIE 2.1: Testowanie zabezpieczonych endpointów --- [cite: 29]

    @Test
    void unauthorizedUserShouldBeRedirectedToLogin() throws Exception {
        // Niezalogowany użytkownik próbuje wejść -> powinien zostać przekierowany (302)
        mockMvc.perform(get("/notes"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "testowyUser", roles = "USER")
    void authorizedUserShouldAccessNotes() throws Exception {
        // Zalogowany użytkownik próbuje wejść -> powinien dostać stronę (200 OK)
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk());
    }

    // --- ZADANIE 2.2: Testowanie CSRF (Ochrona formularzy) ---

    @Test
    @WithMockUser // Jesteśmy zalogowani, ALE...
    void postRequestWithoutCsrfShouldBeForbidden() throws Exception {
        // ...próbujemy wysłać formularz BEZ tokena CSRF -> Serwer musi to odrzucić (403 Forbidden)
        mockMvc.perform(post("/notes")
                        .param("title", "Hacked Note")
                        .param("content", "Malicious Content"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void postRequestWithCsrfShouldSucceed() throws Exception {
        // Próbujemy wysłać formularz Z poprawnym tokenem CSRF -> Powinno zadziałać (Przekierowanie po sukcesie)
        mockMvc.perform(post("/notes")
                        .with(csrf()) // To "magiczna" metoda dodająca token bezpieczeństwa [cite: 39]
                        .param("title", "Legal Note")
                        .param("content", "Safe Content"))
                .andExpect(status().is3xxRedirection());
    }
}
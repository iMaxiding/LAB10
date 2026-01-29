package com.example.project1.service;

import com.example.project1.model.User;
import com.example.project1.repository.UserRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldHashPasswordAndSaveUserSuccessfully() {
        // Zadanie 1.1 [cite: 18]
        User testUser = new User("testUser", "test@example.com", "plainPassword123");
        String hashed = "hashed_password_xyz";

        when(passwordEncoder.encode("plainPassword123")).thenReturn(hashed);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User savedUser = userService.createUser(testUser);

        assertNotNull(savedUser);
        assertEquals(hashed, savedUser.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void validationShouldFailForShortPassword() {
        // Zadanie 1.2 [cite: 22]
        User user = new User("user", "test@test.pl", "123"); // Za krótkie hasło
        var violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Powinien być błąd walidacji dla krótkiego hasła");
    }

    @Test
    void validationShouldFailForInvalidEmail() {
        // Zadanie 1.2 [cite: 20]
        User user = new User("user", "zly-email", "password123");
        var violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Powinien być błąd walidacji dla złego formatu email");
    }
}
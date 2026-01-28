package com.example.project1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // SQLite trzyma created_at jako TEXT (ustawiane w DB DEFAULT datetime('now'))
    // więc tutaj możemy to pominąć, albo dodać jako String.
}

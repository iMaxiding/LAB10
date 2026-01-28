package com.example.project1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateNoteRequest(
        @NotBlank
        @Size(max = 255)
        String title,

        @NotBlank
        @Size(max = 5000)
        String content
) {}


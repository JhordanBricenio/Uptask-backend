package com.codej.uptask.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String email,
                               @NotBlank String password) {
}

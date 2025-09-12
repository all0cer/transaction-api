package com.portifolio.api_transacao.core.Authorization.DTO;

import com.portifolio.api_transacao.entities.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @NotNull
        @Size(max = 50)
        @Pattern(regexp = "[a-zA-Z0-9]+")
        String login,

        @NotBlank
        @NotNull
        String password,

        @NotNull
        UserRole role) {
}

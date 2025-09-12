package com.portifolio.api_transacao.core.Authorization.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginResponseRequest(
        @NotBlank @NotNull
        String token) {

}

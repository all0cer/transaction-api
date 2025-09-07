package com.portifolio.api_transacao.core.Authorization.DTO;

import com.portifolio.api_transacao.entities.user.UserRole;

public record RegisterRequest(String login, String password, UserRole role) {
}

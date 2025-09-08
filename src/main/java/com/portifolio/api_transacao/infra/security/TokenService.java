package com.portifolio.api_transacao.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.portifolio.api_transacao.entities.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;


    public String generateToken(User user) {
        String jti = UUID.randomUUID().toString(); //UTILIZAÇÃO PARA REDIS
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("api-transaction")
                    .withSubject(user.getLogin())
                    .withJWTId(jti)
                    .withExpiresAt(generateExpiresDateToken())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }

    }

    public Optional<String> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var decoded = JWT.require(algorithm)
                    .withIssuer("api-transaction")
                    .build()
                    .verify(token)
                    .getSubject();

            String subject = decoded;
            if(subject == null || subject.isBlank()){ return Optional.empty(); }
            return Optional.of(subject);

        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

    private Instant generateExpiresDateToken() {
         return Instant.now().plus(2, ChronoUnit.HOURS);
    }
}

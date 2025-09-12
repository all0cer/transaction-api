package com.portifolio.api_transacao.controller.TransactionApi;


import com.portifolio.api_transacao.core.Authorization.DTO.AuthorizationRequest;
import com.portifolio.api_transacao.core.Authorization.DTO.LoginResponseRequest;
import com.portifolio.api_transacao.core.Authorization.DTO.RegisterRequest;
import com.portifolio.api_transacao.entities.user.User;
import com.portifolio.api_transacao.infra.security.TokenService;
import com.portifolio.api_transacao.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Login and registration endpoint")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Operation(summary = "Login", description = "Authenticates a user and return a token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = @Content(schema = @Schema(implementation = LoginResponseRequest.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "422", description = "Validação do request falhou")
    })

    @PostMapping("/login")
    public ResponseEntity<LoginResponseRequest> login(@RequestBody @Valid AuthorizationRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var authentication = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseRequest(token));

    }


    @Operation(summary = "Register user", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Login já existe"),
            @ApiResponse(responseCode = "422", description = "Validação do request falhou"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        System.out.println(request.login());
        if (this.userRepository.findByLogin(request.login()) != null)
            return ResponseEntity.badRequest().body("Já existe um usuário cadastrado com este login");

        var encriptedPassword = passwordEncoder.encode(request.password());
        User newUser = new User(request.login(), encriptedPassword, request.role());

        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}

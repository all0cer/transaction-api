package com.portifolio.api_transacao.controller.TransactionApi;


import com.portifolio.api_transacao.core.Authorization.DTO.AuthorizationRequest;
import com.portifolio.api_transacao.core.Authorization.DTO.LoginResponseRequest;
import com.portifolio.api_transacao.core.Authorization.DTO.RegisterRequest;
import com.portifolio.api_transacao.entities.user.User;
import com.portifolio.api_transacao.infra.security.TokenService;
import com.portifolio.api_transacao.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseRequest> login(@RequestBody @Valid AuthorizationRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var authentication = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseRequest(token));

    }


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

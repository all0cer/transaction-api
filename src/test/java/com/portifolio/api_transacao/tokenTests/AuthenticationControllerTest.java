//package com.portifolio.api_transacao.tokenTests;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.portifolio.api_transacao.controller.TransactionApi.AuthenticationController;
//import com.portifolio.api_transacao.core.Authorization.DTO.AuthorizationRequest;
//import com.portifolio.api_transacao.core.Authorization.DTO.RegisterRequest;
//import com.portifolio.api_transacao.entities.user.User;
//import com.portifolio.api_transacao.entities.user.UserRole;
//import com.portifolio.api_transacao.infra.security.TokenService;
//import com.portifolio.api_transacao.repositories.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AuthenticationController.class)
//class AuthenticationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private User user;
//
//    @BeforeEach
//    void setup() {
//        user = new User("testuser", "encodedpass", UserRole.USER);
//    }
//
//    @Test
//    void login_DeveRetornarToken_QuandoCredenciaisValidas() throws Exception {
//        var request = new AuthorizationRequest("testuser", "123");
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//
//        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//        when(tokenService.generateToken(user)).thenReturn("fake-jwt-token");
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
//
//        verify(authenticationManager, times(1)).authenticate(any());
//        verify(tokenService, times(1)).generateToken(user);
//    }
//
//    @Test
//    void register_DeveCadastrarNovoUsuario_QuandoNaoExistente() throws Exception {
//        var request = new RegisterRequest("newuser", "123", UserRole.USER);
//
//        when(userRepository.findByLogin("newuser")).thenReturn(null);
//        when(passwordEncoder.encode("123")).thenReturn("encoded123");
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk());
//
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    void register_DeveRetornarBadRequest_QuandoUsuarioJaExistir() throws Exception {
//        var request = new RegisterRequest("testuser", "123", UserRole.USER);
//
//        when(userRepository.findByLogin("testuser")).thenReturn(user);
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Já existe um usuário cadastrado com este login"));
//
//        verify(userRepository, never()).save(any(User.class));
//    }
//}
//

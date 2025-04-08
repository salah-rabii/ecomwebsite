package com.myecommerce.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myecommerce.controller.AccountController;
import com.myecommerce.model.RegisterDto;
import com.myecommerce.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    private MockMvc mockMvc;   // est un outil fourni par Spring pour tester les contrôleurs HTTP sans démarrer un serveur web réel.

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();   // Cette méthode configure un environnement de test isolé pour le contrôleur accountController
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setConfirmPassword("password123");

        when(userService.registerUser(any(RegisterDto.class), eq(false)))
                .thenReturn("Registration successful!");

        mockMvc.perform(post("/api/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Inscription réussie"));
    }

    @Test
    public void testRegisterUser_PasswordTooShort() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("test@example.com");
        dto.setPassword("123");
        dto.setConfirmPassword("123");

        mockMvc.perform(post("/api/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Le mot de passe doit contenir au moins 6 caractères"));
    }


    @Test
    public void testlogin_form() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("test@example.com");
        dto.setPassword("1236667");


        mockMvc.perform(post("/api/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("L'email n'est pas enregistré"));
    }



    @Test
    public void test_loginform() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("test@example.com");
        dto.setPassword("124");


        mockMvc.perform(post("/api/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Le mot de passe doit contenir au moins 6 caractères"));
    }

    @Test
    public void test_loginform_empty() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("test@example.com");
        dto.setPassword("");


        mockMvc.perform(post("/api/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Le mot de passe ne doit pas être vide"));
    }





}
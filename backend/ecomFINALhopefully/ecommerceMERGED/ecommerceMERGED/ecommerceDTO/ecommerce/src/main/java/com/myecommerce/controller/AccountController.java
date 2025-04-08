package com.myecommerce.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.myecommerce.model.AppUser;
import com.myecommerce.model.RegisterDto;
import com.myecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private UserService userService;


//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Injection de l'AuthenticationManager géré par Spring Security
    @Autowired
    private AuthenticationManager authenticationManager;



    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(
            @Valid @RequestBody RegisterDto registerDto,
            BindingResult bindingResult,
            @RequestParam(name = "isAdmin", defaultValue = "false") boolean isAdmin) {

        Map<String, String> response = new HashMap<>();

        // Vérifier les erreurs de validation
        if (bindingResult.hasErrors()) {
            response.put("error", "Validation failed: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return ResponseEntity.badRequest().body(response);
        }

        // Vérifier longueur du mot de passe
        if (registerDto.getPassword().length() < 6) {
            response.put("error", "Le mot de passe doit contenir au moins 6 caractères");
            return ResponseEntity.badRequest().body(response);
        }

        // Vérifier si les mots de passe correspondent
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            response.put("error", "Les mots de passe ne correspondent pas");
            return ResponseEntity.badRequest().body(response);
        }

        // Enregistrer l'utilisateur avec le rôle approprié
        try {
            String result = userService.registerUser(registerDto, isAdmin);

            if (result.equals("Registration successful!")) {
                response.put("message", "Inscription réussie");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", result);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("error", "Erreur lors de l'inscription : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/register")
    public ResponseEntity<Map<String, String>> showRegistrationForm() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Registration form endpoint");
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/login")
//    public ResponseEntity<Map<String, String>> showLoginForm() {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Login form endpoint");
//        return ResponseEntity.ok(response);
//    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "L'email ne doit pas être vide"));
        }

        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Le mot de passe ne doit pas être vide"));
        }

        if (password.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Le mot de passe doit contenir au moins 6 caractères"));
        }

        if (!userService.doesEmailExist(email)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "L'email n'est pas enregistré"));
        }

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, password);

            // Authentification via AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(authToken);

            // Stockage de l'Authentication dans le SecurityContext pour gérer la session
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Récupération du rôle de l'utilisateur depuis l'objet Authentication
            String role = authentication.getAuthorities().toString();

            Map<String, String> response = new HashMap<>();
            response.put("message", "Authentification réussie");
            response.put("role", role);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email ou mot de passe invalide"));
        }
    }



//    @PostMapping("/login")
//    public ResponseEntity<?> login() {
//            System.out.println("hello world");
//            return ResponseEntity.ok("coco cava");
//    }


    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }




}
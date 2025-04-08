package com.myecommerce.service;


import jakarta.transaction.Transactional;
import com.myecommerce.model.AppUser;
import com.myecommerce.model.RegisterDto;
import com.myecommerce.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {



    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser trouverParId(Long userId) {
        return appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));
    }


    @Transactional
    public String registerUser(RegisterDto registerDto, boolean isAdmin) {

        if (appUserRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            return "Email is already registered.";
        }

        AppUser newUser = new AppUser();
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());


        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        if (isAdmin) {
            roles.add("ROLE_ADMIN");
        }
        newUser.setRoles(roles);

        appUserRepository.save(newUser);
        return "Registration successful!";
    }

    @Transactional
    public boolean authenticateUser(String email, String password) {
        // Retrieve the user by email
        var userOptional = appUserRepository.findByEmail(email);

        // If user is not found, return false
        if (userOptional.isEmpty()) {
            return false; // Email not registered
        }

        // Get the user
        AppUser user = userOptional.get();

        // Validate the password (compare raw password with the hashed password stored in the database)
        return passwordEncoder.matches(password, user.getPassword());
    }

    public boolean doesEmailExist(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    // Méthode pour récupérer un utilisateur par son email
    public AppUser getUserByEmail(String email) {
        Optional<AppUser> optionalUser = appUserRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new RuntimeException("User not found with email: " + email);
    }

    // Méthode utilitaire pour récupérer directement le rôle d'un utilisateur par son email
    public String getRoleByEmail(String email) {
        AppUser user = getUserByEmail(email);
        return user.getRoles().toString();
    }

}

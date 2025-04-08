package com.myecommerce.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DashboardController {

    @GetMapping("/api/dashboard")
    public ResponseEntity<String> showDashboard() {
        return ResponseEntity.ok().body("Tableau de bord utilisateur");
    }
}
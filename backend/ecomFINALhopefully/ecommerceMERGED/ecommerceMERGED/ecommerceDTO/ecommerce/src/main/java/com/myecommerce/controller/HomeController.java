package com.myecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api")
    public ResponseEntity<String> index(){


        return ResponseEntity.ok().body("Page d'accueil");
    }

    @GetMapping("/api/success")
    public ResponseEntity<String> success(){
        return ResponseEntity.ok().body("Paiement réussi");
    }

    @GetMapping("/api/cancel")
    public ResponseEntity<String> cancel(){
        return ResponseEntity.ok().body("Paiement annulé");
    }
}

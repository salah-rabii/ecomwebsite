package com.myecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = "test")
public class test {
    @GetMapping(path="String")
    public String getString(){
        return "chaine de caractere transmise par SA";
    }

}

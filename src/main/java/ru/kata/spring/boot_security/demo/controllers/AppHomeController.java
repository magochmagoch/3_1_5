package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppHomeController {

    @GetMapping("/admin")
    public String showIndexPageAdmin() {
        return "admin_page";
    }

    @GetMapping("/user")
    public String showIndexPageUser() {
        return "user";
    }

}
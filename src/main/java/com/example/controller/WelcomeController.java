package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/user/welcome")
    public String userWelcome() {
        return "welcome";
    }

    @GetMapping("/admin/welcome")
    public String adminWelcome() {
        return "welcome";
    }



}

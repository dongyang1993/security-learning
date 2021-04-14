package com.example.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/index")
@RestController
public class IndexController {
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/public")
    public void index(@RequestParam("username") String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println("");
    }
}

package com.example.TP4.web;

import com.example.TP4.userService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Controller
public class userControllerExemple {

    @Autowired
    private userService userService;

    @GetMapping
    @RequestMapping("getE")
    public String getAll() {
         System.out.println(userService.getAllUsers());
         return "index";
    }

    @PostMapping
    public void create(@RequestParam String name) {
        userService.createUser(name);
    }
}

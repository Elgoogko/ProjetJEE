package com.msclient.Controller;

import com.msclient.Entity.Role;
import com.msclient.Entity.User;
import com.msclient.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String surname) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);

        userRepository.save(user);

        return "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "auth/register";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "auth/login";
    }
}

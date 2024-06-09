package com.blog.blogapi.controller;

import com.blog.blogapi.application.login.LoginRequest;
import com.blog.blogapi.application.login.LoginResult;
import com.blog.blogapi.application.register.RegisterRequest;
import com.blog.blogapi.application.register.RegisterResult;
import com.blog.blogapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResult register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public LoginResult login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}

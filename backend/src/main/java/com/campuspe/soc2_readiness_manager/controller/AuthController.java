package com.campuspe.soc2_readiness_manager.controller;

import com.campuspe.soc2_readiness_manager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) {
        return service.register(body.get("email"), body.get("password"));
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        return service.login(body.get("email"), body.get("password"));
    }

    // REFRESH
    @PostMapping("/refresh")
    public String refresh(@RequestBody Map<String, String> body) {
        return service.refresh(body.get("token"));
    }
}
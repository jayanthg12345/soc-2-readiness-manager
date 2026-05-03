package com.campuspe.soc2_readiness_manager.service;

import com.campuspe.soc2_readiness_manager.entity.User;
import com.campuspe.soc2_readiness_manager.repository.UserRepository;
import com.campuspe.soc2_readiness_manager.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // REGISTER
    public String register(String email, String password) {

        if (repo.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(
                UUID.randomUUID(),
                email,
                encoder.encode(password),
                "VIEWER"
        );

        repo.save(user);

        return "User registered";
    }

    // LOGIN
    public String login(String email, String password) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(email);
    }

    // REFRESH
    public String refresh(String token) {
        String email = jwtUtil.extractEmail(token);
        return jwtUtil.generateToken(email);
    }
}
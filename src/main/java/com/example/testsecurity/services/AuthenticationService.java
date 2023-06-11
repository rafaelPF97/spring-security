package com.example.testsecurity.services;

import com.example.testsecurity.controllers.AuthenticationResponse;
import com.example.testsecurity.controllers.dto.AuthDTO;
import com.example.testsecurity.controllers.dto.RegisterDTO;
import com.example.testsecurity.models.Role;
import com.example.testsecurity.models.UserModel;
import com.example.testsecurity.repositores.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterDTO login) {
        var user = UserModel.builder()
                .username(login.username())
                .email(login.email())
                .password(passwordEncoder.encode(login.password()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthDTO login) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(login.username(), login.password())
        );
        var user = repository.findByUsername(login.username())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

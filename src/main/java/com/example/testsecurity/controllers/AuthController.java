package com.example.testsecurity.controllers;

import com.example.testsecurity.controllers.dto.AuthDTO;
import com.example.testsecurity.controllers.dto.RegisterDTO;
import com.example.testsecurity.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDTO login) {
        return ResponseEntity.ok(service.register(login));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse>  authenticate(@RequestBody AuthDTO login) {
        return ResponseEntity.ok(service.authenticate(login));
    }



}

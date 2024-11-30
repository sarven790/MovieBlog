package com.example.filmblogproject.interfaceAdapters.controller;

import com.example.filmblogproject.application.service.AuthService;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.*;
import com.example.filmblogproject.interfaceAdapters.dto.response.service.AuthResponse;
import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody RegisterRequest request) throws DataException {
        return service.saveUser(request);
    }


    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) throws DataException {
        return service.forgotPassword(request);
    }

    @PostMapping("/changePassword")
    public String changeNewPassword(@RequestBody ChangeNewPasswordRequest request) throws DataException {
        return service.changePassword(request);
    }

    @PostMapping("/login")
    public AuthResponse getToken(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RequestByString request) {
        service.logout(request);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
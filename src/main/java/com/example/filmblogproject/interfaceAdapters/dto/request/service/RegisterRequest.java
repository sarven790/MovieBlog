package com.example.filmblogproject.interfaceAdapters.dto.request.service;

import com.example.filmblogproject.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

public record RegisterRequest(
        String name,
        String surname,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        Date birth,
        //@CustomValid(message = "This email is already in use")
        String email,
        String password,
        String phoneNumber,
        String address,
        List<String> skillCodes,
        List<String> educationCodes,
        Set<Role> authorities

        ) {
}

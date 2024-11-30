package com.example.filmblogproject.interfaceAdapters.dto.request.service;

import java.util.List;

public record SessionCredential(
        String name,
        String surname,
        String email,
        List<String> roles
) {
}

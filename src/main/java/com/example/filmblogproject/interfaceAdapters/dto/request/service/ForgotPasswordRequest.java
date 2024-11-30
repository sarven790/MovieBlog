package com.example.filmblogproject.interfaceAdapters.dto.request.service;

public record ForgotPasswordRequest(
        String userName,
        String newPassword,
        Boolean isMail
) {
}

package com.example.filmblogproject.interfaceAdapters.dto.request.service;

public record ChangeNewPasswordRequest(
        String userName,
        String oldPassword,
        String newPassword,
        Boolean isMail
) {
}

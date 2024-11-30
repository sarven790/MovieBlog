package com.example.filmblogproject.interfaceAdapters.dto.request.service;


import com.example.filmblogproject.domain.entity.UserCredential;

public record PasswordHistoryRequest(UserCredential credential, String password) {
}

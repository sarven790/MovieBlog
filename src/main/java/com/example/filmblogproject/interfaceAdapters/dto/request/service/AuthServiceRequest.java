package com.example.filmblogproject.interfaceAdapters.dto.request.service;

import com.example.filmblogproject.application.service.PasswordHistoryService;
import com.example.filmblogproject.application.service.UserDetailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthServiceRequest {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private PasswordHistoryService passwordHistoryService;

}

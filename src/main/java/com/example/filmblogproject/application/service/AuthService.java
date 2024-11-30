package com.example.filmblogproject.application.service;


import com.example.filmblogproject.application.utils.EncryptionUtil;
import com.example.filmblogproject.application.utils.GenerateSessionId;
import com.example.filmblogproject.application.utils.PasswordValidator;
import com.example.filmblogproject.domain.entity.UserCredential;
import com.example.filmblogproject.domain.enums.Role;
import com.example.filmblogproject.domain.repository.UserCredentialRepository;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.*;
import com.example.filmblogproject.interfaceAdapters.dto.response.service.AuthResponse;
import com.example.filmblogproject.interfaceAdapters.dto.response.service.ResponseByBoolean;
import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepository repository;
    private final AuthServiceRequest authServiceRequest;

    private final JwtService jwtService;


    private HashMap<String, UserCredential> credentialHashMap= new HashMap<>();
    private String sessionId;

    public void fillToMap(String userName) throws DataException {
        Optional<UserCredential> optUserCredential = repository.findByName(userName);
        if (optUserCredential.isPresent()) {
            UserCredential credential = optUserCredential.get();
            credentialHashMap.put(credential.getName(), credential);
        }
    }

    @Transactional
    public String saveUser(RegisterRequest request) throws DataException{
        PasswordValidator.validatePasswordToSequential(request.password());
        UserCredential credential = new UserCredential();
        credential.setPassword(authServiceRequest.getPasswordEncoder().encode(request.password()));
        try {
            credential.setEmail(EncryptionUtil.encrypt(request.email()));
        }catch (Exception ex) {
            log.error("Somthing is wrong Exception: {}", ex.getMessage());
        }
        credential.setName(request.name());
        credential.setSurname(request.surname());
        credential.setBirth(request.birth());
        credential.setAuthorities(request.authorities());
        credential = repository.save(credential);

        if (credential.getId() != null) {
            saveAll(request, credential);
        }

        return "user added to the system";
    }

    @Async
    public void saveAll(RegisterRequest request, UserCredential credential) throws DataException{
        authServiceRequest.getPasswordHistoryService().saveHistory(new PasswordHistoryRequest(credential, credential.getPassword()));
        authServiceRequest.getUserDetailService().save(request,credential);
    }

    public String forgotPassword(ForgotPasswordRequest request) throws DataException{

        PasswordValidator.validatePasswordToSequential(request.newPassword());

        ResponseByBoolean responseByBoolean = historyControl(new HistoryControlRequest(request.userName(), request.newPassword()));

        if (responseByBoolean.value()) {
            throw new DataException("Use a password that you have not used before.");
        }

        UserCredential credential = credentialHashMap.get(request.userName());
        credential.setPassword(authServiceRequest.getPasswordEncoder().encode(request.newPassword()));
        repository.save(credential);
        authServiceRequest.getPasswordHistoryService().saveHistory(new PasswordHistoryRequest(credential, authServiceRequest.getPasswordEncoder().encode(request.newPassword())));
        credentialHashMap.clear();
        return "Your password has been changed successfully.";

    }

    public String changePassword(ChangeNewPasswordRequest request) throws DataException{

        PasswordValidator.validatePasswordToSequential(request.newPassword());

        if (request.newPassword().equals(request.oldPassword())) {
            throw new DataException("Your new password cannot be the same as your old password.");
        }

        ResponseByBoolean responseByBoolean = historyControl(new HistoryControlRequest(request.userName(), request.newPassword()));

        if (responseByBoolean.value()) {
            throw new DataException("Use a password that you have not used before.");
        }

        UserCredential credential = credentialHashMap.get(request.userName());
        credential.setPassword(authServiceRequest.getPasswordEncoder().encode(request.newPassword()));
        repository.save(credential);
        authServiceRequest.getPasswordHistoryService().saveHistory(new PasswordHistoryRequest(credential, authServiceRequest.getPasswordEncoder().encode(request.newPassword())));
        credentialHashMap.clear();
        return "Your password has been changed successfully.";
    }

    private ResponseByBoolean historyControl(HistoryControlRequest request) throws DataException{

        fillToMap(request.userName());
        UserCredential credential = credentialHashMap.get(request.userName());
        PasswordHistoryRequest passwordHistoryRequest = new PasswordHistoryRequest(credential,request.password());
        return authServiceRequest.getPasswordHistoryService().findByPassword(passwordHistoryRequest);

    }

    public void logout(RequestByString request) {
        jwtService.addToBlackList(request);
    }

    public AuthResponse generateToken(String username) throws JsonProcessingException {
        Optional<UserCredential> credential = repository.findByName(username);
        UserCredential userCredential = credential.get();
        Set<Role> roleSet = userCredential.getAuthorities();
        List<String> roles = new ArrayList<>();
        roleSet.forEach(role -> {
            roles.add(role.name());
        });
        SessionCredential sessionCredential = new SessionCredential(
                userCredential.getName(),
                userCredential.getSurname(),
                userCredential.getEmail(),
                roles);
        sessionId = GenerateSessionId.generate();
        String token = jwtService.generateToken(username, sessionId, roles);
        return new AuthResponse(token,sessionId);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
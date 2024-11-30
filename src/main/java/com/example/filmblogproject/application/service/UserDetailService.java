package com.example.filmblogproject.application.service;


import com.example.filmblogproject.application.utils.EncryptionUtil;
import com.example.filmblogproject.domain.entity.UserCredential;
import com.example.filmblogproject.domain.entity.UserDetail;
import com.example.filmblogproject.domain.repository.UserDetailRepository;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;

    public void save(RegisterRequest request, UserCredential credential) {
        UserDetail detail = new UserDetail();
        try {
            detail.setAddress(EncryptionUtil.encrypt(request.address()));
            detail.setPhoneNumber(EncryptionUtil.encrypt(request.phoneNumber()));
        }catch (Exception ex) {
            log.error("Somthing is wrong Exception: {}", ex.getMessage());
        }
        detail.setStatusCode(1);
        detail.setUserCredential(credential);
        userDetailRepository.save(detail);
    }

}

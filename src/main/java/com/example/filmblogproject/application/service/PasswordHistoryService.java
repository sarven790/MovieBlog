package com.example.filmblogproject.application.service;


import com.example.filmblogproject.domain.entity.PasswordHistory;
import com.example.filmblogproject.domain.repository.PasswordHistoryRepository;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.PasswordHistoryRequest;
import com.example.filmblogproject.interfaceAdapters.dto.response.service.ResponseByBoolean;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordHistoryService {

    private final PasswordHistoryRepository passwordHistoryRepository;
    private final PasswordEncoder encoder;
    public void saveHistory(PasswordHistoryRequest request) {

        PasswordHistory history = new PasswordHistory();
        history.setUserCredential(request.credential());
        history.setPassword(request.password());

        passwordHistoryRepository.save(history);
    }

    public ResponseByBoolean findByPassword(PasswordHistoryRequest request) {

        PasswordHistory history = passwordHistoryRepository.findByUserCredentialNameEqualsIgnoreCase(request.credential().getName());

        if (history != null) {

            if (encoder.matches(request.password(), history.getPassword())) {
                return new ResponseByBoolean(true);
            }
            return new ResponseByBoolean(false);

        }else
            return new ResponseByBoolean(false);

    }

}

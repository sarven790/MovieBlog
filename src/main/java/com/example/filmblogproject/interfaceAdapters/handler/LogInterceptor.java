package com.example.filmblogproject.interfaceAdapters.handler;

import com.example.filmblogproject.domain.entity.ServiceLog;
import com.example.filmblogproject.domain.repository.ServiceLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    private ServiceLogRepository serviceLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Benzersiz bir transaction ID oluşturuluyor
        String transactionId = UUID.randomUUID().toString();

        // Step: İlgili işlem adımı (örneğin, endpoint adı)
        String step = request.getRequestURI();

        // Log mesajı ve detaylarını oluşturuyoruz
        String message = "Incoming request to: " + step;
        String details = "Method: " + request.getMethod() + ", Params: " + request.getQueryString();

        // Log kaydını veritabanına kaydediyoruz
        ServiceLog log = new ServiceLog(transactionId, step, message, details, System.currentTimeMillis());
        serviceLogRepository.save(log);

        // Request'e transactionId'yi ekleyelim (response'ı loglamak için)
        request.setAttribute("transactionId", transactionId);

        return true; // Request'i devam ettiriyoruz
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TransactionId'yi alıyoruz
        String transactionId = (String) request.getAttribute("transactionId");

        // Step: İşlem adımı
        String step = request.getRequestURI();

        // Hata mesajı ve yanıtı logluyoruz
        String message = "Response for request to: " + step;
        String details = "Response status: " + response.getStatus();

        if (ex != null) {
            message = "Error occurred for request to: " + step;
            details = "Error message: " + ex.getMessage();
        }

        // Log kaydını veritabanına kaydediyoruz
        ServiceLog log = new ServiceLog(transactionId, step, message, details, System.currentTimeMillis());
        serviceLogRepository.save(log);
    }
}

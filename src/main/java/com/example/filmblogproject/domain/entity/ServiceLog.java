package com.example.filmblogproject.domain.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class ServiceLog extends BaseEntity{

    private String transactionId;
    private String step;
    private String message;
    private String details;
    private Long timestamp;
    private Long createdAt;

    public ServiceLog(String transactionId, String step, String message, String details, Long timestamp) {
        this.transactionId = transactionId;
        this.step = step;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
        this.createdAt = System.currentTimeMillis();
    }

}

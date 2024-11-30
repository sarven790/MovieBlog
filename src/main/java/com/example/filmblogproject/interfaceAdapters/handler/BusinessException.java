package com.example.filmblogproject.interfaceAdapters.handler;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }

}

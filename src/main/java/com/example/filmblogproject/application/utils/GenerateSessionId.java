package com.example.filmblogproject.application.utils;


import java.util.UUID;

public abstract class GenerateSessionId {

    public static String generate() {
        return UUID.randomUUID().toString();
    }

}

package com.example.filmblogproject.application.utils;

import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class PasswordValidator {

    public static void validatePasswordToSequential(String password) {

        boolean isSequential = isSequentialPassword(password);

        if (isSequential)
            throw new DataException("The password you entered cannot be a sequential number.");
    }

    private boolean isSequentialPassword(String password) {
        // Şifre en az 2 karakter içermeli
        if (password.length() < 2) {
            return false;
        }

        // Tüm karakterlerin sayı olup olmadığını kontrol et
        for (int i = 0; i < password.length() - 1; i++) {
            char currentChar = password.charAt(i);
            char nextChar = password.charAt(i + 1);

            // Eğer iki karakter ardışık olarak artan veya azalan bir sayı ise
            if (Character.isDigit(currentChar) && Character.isDigit(nextChar)) {
                int current = Character.getNumericValue(currentChar);
                int next = Character.getNumericValue(nextChar);

                // Artış ya da azalış varsa ardışıklığı tespit et
                if ((next == current + 1) || (next == current - 1)) {
                    continue;
                } else {
                    return false; // Ardışık değilse
                }
            } else {
                return false; // Sayı değilse
            }
        }
        return true; // Tüm rakamlar ardışıksa
    }

}

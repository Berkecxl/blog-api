package com.blog.blogapi.validate;

import com.blog.blogapi.application.register.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {
    public String validateRegister(RegisterRequest request) {
        if (request.getUsername().length() <= 4) {
            return "Kullanıcı adı en az 4 karakter içermelidir.";
        }
        if (request.getPassword().length() <= 8){
            return "Şifre en az 8 karakter içermelidir.";
        }
        if (request.getPassword().chars().noneMatch(Character::isUpperCase)){
            return "Şifre en az 1 büyük karakter içermelidir.";
        }

        return null;
    }
}

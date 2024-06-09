package com.blog.blogapi.application.register;

import org.springframework.stereotype.Component;

@Component
public class RegisterResultAssembler {
    public RegisterResult applyNotValidResult(String message) {

        return new RegisterResult(
                false,
                message
        );
    }

    public RegisterResult applyUserExistResult() {

        return new RegisterResult(
                false,
                "Bu kullanıcı adı ile kayıt bulunmaktadır."
        );
    }

    public RegisterResult applySuccessResult() {
        return new RegisterResult(
                true,
                null
        );
    }
}

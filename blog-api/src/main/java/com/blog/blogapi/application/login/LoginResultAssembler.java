package com.blog.blogapi.application.login;

import org.springframework.stereotype.Component;

@Component
public class LoginResultAssembler {

    public LoginResult applyFailureResult(String message) {
        return new LoginResult(
                false,
                message,
                null
        );
    }

    public LoginResult applySuccessResult(String jwtToken) {
        return new LoginResult(
                true,
                null,
                jwtToken
        );
    }
}

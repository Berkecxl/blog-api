package com.blog.blogapi.application.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResult {
    private boolean success;
    private String resultMessage;
}

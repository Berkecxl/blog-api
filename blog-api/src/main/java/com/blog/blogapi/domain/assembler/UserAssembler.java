package com.blog.blogapi.domain.assembler;

import com.blog.blogapi.application.register.RegisterRequest;
import com.blog.blogapi.domain.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserAssembler {

    public User applyUserWithRegisterRequest(RegisterRequest request, String encodedPassword) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPassword(encodedPassword);
        user.setEmail(request.getEmail());
        user.setActive(true);
        user.setCreatedDate(LocalDateTime.now());

        return user;
    }
}

package com.blog.blogapi.application.messagecreate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageCreateRequest {
    private String name;
    private String surname;
    private String email;
    private String message;
}

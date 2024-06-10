package com.blog.blogapi.domain.assembler;

import com.blog.blogapi.application.messagecreate.MessageCreateRequest;
import com.blog.blogapi.domain.model.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageAssembler {
    public Message apply(MessageCreateRequest request) {
        Message message = new Message();

        message.setMessage(request.getMessage());
        message.setName(request.getName());
        message.setSurname(request.getSurname());
        message.setEmail(request.getEmail());
        message.setCreatedDate(LocalDateTime.now());
        return message;
    }
}
